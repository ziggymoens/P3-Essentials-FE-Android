package com.hogentessentials1.essentials.ui.changeGroup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hogentessentials1.essentials.R
import com.hogentessentials1.essentials.data.model.ChangeGroup
import com.hogentessentials1.essentials.databinding.TeamsFragmentBinding
import com.hogentessentials1.essentials.ui.LoadingFragment
import com.hogentessentials1.essentials.util.Status
import org.koin.android.ext.android.inject

/**
 * @author Simon De Wilde
 *
 * Fragment for showing the overview of teams
 */
class TeamsFragment : Fragment(), ChangeGroupClickListener {

    private val loadingDialogFragment by lazy { LoadingFragment() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = "Teams"

        val binding: TeamsFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.teams_fragment, container, false)

        val viewModel: TeamsViewModel by inject()

        binding.viewmodel = viewModel

        binding.lifecycleOwner = this

        val adapter = ChangeGroupAdapter(this)

        binding.teamsRV.adapter = adapter

        viewModel.changeGroups.observe(
            viewLifecycleOwner,
            {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            showLoading(false)
                            if (resource.data?.isEmpty() == true) {
                                binding.noTeamsBanner.visibility = View.VISIBLE
                            } else {
                                binding.noTeamsBanner.visibility = View.GONE
                            }
                            adapter.submitList(resource.data)
                        }
                        Status.LOADING -> {
                            showLoading(true)
                            binding.noTeamsBanner.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            showLoading(false)
                            // binding.noTeamsBanner.visibility = View.VISIBLE
                        }
                    }
                }
            }
        )

        return binding.root
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            if (!loadingDialogFragment.isAdded) {
                loadingDialogFragment.show(requireActivity().supportFragmentManager, "loader")
            }
        } else {
            // if (loadingDialogFragment.isAdded) {
            loadingDialogFragment.dismissAllowingStateLoss()
            // }
        }
    }

    override fun onClick(changeGroup: ChangeGroup) {
        navigateToDetail(changeGroup)
    }

    private fun navigateToDetail(changeGroup: ChangeGroup) {

        val directions =
            TeamsFragmentDirections.actionTeamsFragmentToTeamDetailsFragment(changeGroup.employeeChangeGroups!!.map { ecg -> ecg.employee!!.firstName.plus(" ").plus(ecg.employee.lastName) }.toTypedArray())
        findNavController().navigate(directions)
    }
}
