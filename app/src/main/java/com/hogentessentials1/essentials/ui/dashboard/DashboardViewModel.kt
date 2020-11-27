package com.hogentessentials1.essentials.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogentessentials1.essentials.data.model.ChangeInitiative
import com.hogentessentials1.essentials.data.model.Repositories.ChangeInitiativeRepository
import com.hogentessentials1.essentials.data.model.Repositories.DashboardRepository
import com.hogentessentials1.essentials.data.model.Repositories.RoadMapRepository
import com.hogentessentials1.essentials.data.model.RoadMapItem
import com.hogentessentials1.essentials.data.model.Survey
import com.hogentessentials1.essentials.data.model.SurveyQuestion
import com.hogentessentials1.essentials.data.model.util.Status
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * @author Marbod Naassens
 *
 * viewmodel voor Dashboard
 */
class DashboardViewModel(private val dashboardRepository: DashboardRepository, private val cirepository: ChangeInitiativeRepository, val rmiRepository: RoadMapRepository) : ViewModel() {

    var chosenCIId: Int = 1;

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _changeInititives = MutableLiveData<List<ChangeInitiative>>()
    val cis: LiveData<List<ChangeInitiative>>
        get() = _changeInititives

    private val _roadMapItems = MutableLiveData<List<RoadMapItem>>()
    val rmis: LiveData<List<RoadMapItem>>
        get() = _roadMapItems

    private val _filledIn = MutableLiveData<Double>()
    val fi: LiveData<Double>
        get() = _filledIn

    private val _mood = MutableLiveData<Map<Int, Int>>()
    val m: LiveData<Map<Int, Int>>
        get() = _mood

    /*var changeInitiatives: ArrayList<ChangeInitiative> = arrayListOf(
        ChangeInitiative(
            title = "New Resto",
            surveys = arrayListOf(
                Survey(
                    name = "Personnel Survey",
                    arrayListOf(
                        SurveyQuestion(
                            question = "What do you think of the new personnel?",
                            option0 = "Uneatable",
                            option5 = "Delicious"
                        ),
                        SurveyQuestion(
                            question = "Would you recommend the new resto to your colleagues?",
                            option0 = "No",
                            option5 = "Yes"
                        )
                    )
                ),
                Survey(
                    name = "Food Survey",
                    arrayListOf(
                        SurveyQuestion(
                            question = "What do you think of the new food?",
                            option0 = "Uneatable",
                            option5 = "Delicious"
                        ),
                        SurveyQuestion(
                            question = "Would you recommend the food to your colleagues?",
                            option0 = "No",
                            option5 = "Yes"
                        )
                    )
                ),
                Survey(
                    name = "Impact Survey",
                    arrayListOf(
                        SurveyQuestion(
                            question = "What do you think this change has an impact on your work?",
                            option0 = "No",
                            option5 = "Yes"
                        ),
                        SurveyQuestion(
                            question = "Would you feel more happy at work?",
                            option0 = "No",
                            option5 = "Yes"
                        )
                    )
                )
            )
        ),
        ChangeInitiative(
            title = "Expansion to German market",
            surveys = arrayListOf(
                Survey(
                    name = "Financial Survey",
                    arrayListOf(
                        SurveyQuestion(
                            question = "What do you think of the new financial arrangement concerning the expansion?",
                            option0 = "Very bad",
                            option5 = "Very Good"
                        ),
                        SurveyQuestion(
                            question = "Do you think you will get a raise by the end of the year?",
                            option0 = "No",
                            option5 = "Yes"
                        )
                    )
                ),
                Survey(
                    name = "Work Survey",
                    arrayListOf(
                        SurveyQuestion(
                            question = "Do you feel more pressure at work?",
                            option0 = "No",
                            option5 = "Yes"
                        ),
                        SurveyQuestion(
                            question = "Would you like working form home while we are expanding?",
                            option0 = "No",
                            option5 = "Yes"
                        )
                    )
                )
            )
        )
    )

    var roadmapItems: ArrayList<RoadmapItem> = arrayListOf(
        RoadmapItem(
            title = "Step 1",
            start = "11-10-2020",
            end = "11-10-2020",
            Survey(
                name = "Work Survey",
                arrayListOf(
                    SurveyQuestion(
                        question = "Do you feel more pressure at work?",
                        option0 = "No",
                        option5 = "Yes"
                    ),
                    SurveyQuestion(
                        question = "Would you like working form home while we are expanding?",
                        option0 = "No",
                        option5 = "Yes"
                    )
                )
            )
        ),
        RoadmapItem(
            title = "Step 2",
            start = "11-10-2020",
            end = "11-10-2020",
            Survey(
                name = "Work Survey",
                arrayListOf(
                    SurveyQuestion(
                        question = "Do you feel more pressure at work?",
                        option0 = "No",
                        option5 = "Yes"
                    ),
                    SurveyQuestion(
                        question = "Would you like working form home while we are expanding?",
                        option0 = "No",
                        option5 = "Yes"
                    )
                )
            )
        )
    )*/

    init {
        viewModelScope.launch {
            _status.value = Status.LOADING
            Timber.e("start met ophalen")
            try {
                Timber.e(chosenCIId.toString())
                _changeInititives.value =
                    cirepository.getChangeInitiativesForChangeManager(6).data
                _roadMapItems.value =
                    rmiRepository.getRoadMaps(chosenCIId).data
                _filledIn.value = dashboardRepository.getFilledInSurveys(chosenCIId).data
                _mood.value = dashboardRepository.getMood(chosenCIId).data

                Timber.e("ophalen successvol")
                _status.value = Status.SUCCESS
            } catch (e: Exception) {
                Timber.e("ophalen mislukt")
                Timber.e("${e.message}")

                _status.value = Status.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    private val _navigateToChangeInitiative = MutableLiveData<ChangeInitiative>()
    val navigateToChangeInitiative
        get() = _navigateToChangeInitiative

    fun onChangeInitiativeClicked(changeInitiative: ChangeInitiative) {
        _navigateToChangeInitiative.value = changeInitiative
    }

    fun onChangeInitiativeNavigated() {
        _navigateToChangeInitiative.value = null
    }
}
