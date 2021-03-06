package com.hogentessentials1.essentials.ui.roadMap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hogentessentials1.essentials.R
import com.hogentessentials1.essentials.data.model.Question
import com.hogentessentials1.essentials.data.model.RoadMapItem
import com.hogentessentials1.essentials.databinding.RoadmapBinding
import com.hogentessentials1.essentials.util.Globals

/**
 * fragment for road maps
 *
 * @author Ziggy Moens
 */
@Suppress("DEPRECATION")
class RoadMapFragment : Fragment() {

    private lateinit var roadmapitem: RoadMapItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: RoadmapBinding =
            DataBindingUtil.inflate(inflater, R.layout.roadmap, container, false)

        val args = RoadMapFragmentArgs.fromBundle(requireArguments())
        roadmapitem = args.roadmapitem
        val changemanager = args.changemanager

        val viewModel = ViewModelProvider(this).get(RoadMapViewModel::class.java)

        val qOriginal: List<Question> = roadmapitem.assessment!!.questions
        val q: ArrayList<Question> = arrayListOf()
        var filledIn = 0

        for (question in roadmapitem.assessment?.questions!!) {
            if (filledIn < question.questionRegistered!!.keys.size)
                filledIn = question.questionRegistered.keys.size
            if (!question.questionRegistered.keys.contains(Globals.userid.toString())) {
                q.add(question)
            }
        }

        binding.amountQuestions.text = qOriginal.size.toString()
        binding.filledIn.text = filledIn.toString()

        roadmapitem.assessment!!.questions = q

        viewModel.roadMapItem = roadmapitem

        binding.viewModel = viewModel

        if (q.size == 0 && !changemanager) {
            binding.surveyRoadmap.setBackgroundColor(resources.getColor(R.color.green))
            binding.surveyRoadmap.text = resources.getText(R.string.survey_already_filled)
        }

        if (!changemanager) {
            viewModel.navigateToSurvey.observe(
                viewLifecycleOwner,
                {
                    if (it) {
                        if (q.size == 0) {
                            roadmapitem.assessment!!.questions = qOriginal
                            binding.root.findNavController().navigate(
                                RoadMapFragmentDirections.actionRoadMapFragmentToSurveyComplete()
                            )
                        } else {
                            binding.root.findNavController().navigate(
                                RoadMapFragmentDirections.actionRoadMapFragmentToSurveyQuestionFragment(
                                    changemanager,
                                    roadmapitem
                                )
                            )
                        }
                        viewModel.onNavigatedToSurvey()
                    }
                }
            )
        } else {
            binding.surveyRoadmap.text = getString(R.string.view_survey)
            viewModel.navigateToSurvey.observe(
                viewLifecycleOwner,
                {
                    if (it) {
                        roadmapitem.assessment!!.questions = qOriginal
                        binding.root.findNavController().navigate(
                            RoadMapFragmentDirections.actionRoadMapFragmentToMyChangesQuestionListFragment(roadmapitem)
                        )
                    }
                }
            )
        }

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.roadmap)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (roadmapitem.assessment == null) {
            view?.findNavController()
                ?.navigate(RoadMapFragmentDirections.actionRoadMapFragmentToNotFoundFragment())
        }
    }
}
