package com.hogentessentials1.essentials.ui.dashboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hogentessentials1.essentials.R
import com.hogentessentials1.essentials.data.model.ChangeInitiative
import com.hogentessentials1.essentials.data.model.RoadMapItem
import com.hogentessentials1.essentials.databinding.FragmentDashboardGraphBinding
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * fragent for a dashboard graph
 *
 */
class DashboardGraphFragment : Fragment() {
    // lateinit var viewModel: DashboardViewModel
    private lateinit var binding: FragmentDashboardGraphBinding
    val viewModel: DashboardViewModel by inject()
    var ci: ChangeInitiative? = null
    var rmi: RoadMapItem? = null
    var mood: Map<Int, Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard_graph,
            container,
            false
        )
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        arguments?.getParcelable<ChangeInitiative>("changeInitiative")?.let {
            ci = it
        }

        binding.chart.setNoDataText("No moods available for Change")
        if (ci != null) {
            viewModel.chosenCIId = ci!!.id
            refreshVM()
            getMood()

            binding.textView2.text = ci?.title
        }

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.title = "Dashboard"

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoFragment -> this.findNavController().navigate(R.id.homeScreenFragment)
            R.id.websiteFragment -> {
                val uri: Uri = Uri.parse("https://essentialstoolkit.netlify.app/")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showCharts() {
        val chart = binding.chart
        chart.description.textSize = 20f
        chart.legend.textSize = 15f
        if (mood != null) {
            val data = PieData(getDataSet())
            chart.data = data
            chart.data.setValueTextSize(15f)
        }
        chart.description.text = getString(R.string.chartDescription)
        chart.setEntryLabelTextSize(20f)
        chart.animateXY(2000, 2000)
        chart.invalidate()
    }

    private fun getDataSet(): PieDataSet? {
        val moods: List<String> = listOf(
            "\uD83D\uDE26",
            "\uD83D\uDE41",
            "\uD83D\uDE10",
            "\uD83D\uDE42",
            "\uD83D\uDE04"
        )
        val valueSet1 = ArrayList<PieEntry>()
        if (mood!!.isNotEmpty()) {
            Timber.e(mood.toString())
            Timber.e("hey")
            var sum = 0
            mood!!.values.forEach { sum += it }
            for (m in mood!!) {
                val ve = PieEntry((m.value.toFloat() / sum) * 100, moods[m.key - 1])
                valueSet1.add(ve)
            }
            val dataSet1 = PieDataSet(valueSet1, getString(R.string.dataSetDescription))
            dataSet1.valueTextSize = 20f
            dataSet1.setColors(*ColorTemplate.JOYFUL_COLORS)
            return dataSet1
        }
        return null
    }

    fun refreshVM() {
        viewModel.fillDashboard()
    }

    fun getMood() {
        viewModel.m.observe(
            viewLifecycleOwner,
            Observer {
                mood = it
                showCharts()
            }
        )
    }
}
