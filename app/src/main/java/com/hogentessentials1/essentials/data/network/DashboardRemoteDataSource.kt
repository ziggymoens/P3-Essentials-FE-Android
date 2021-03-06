package com.hogentessentials1.essentials.data.network

/**
 * The remote data source for dashboard
 *
 * @author Marbod Naassens
 *
 * @property dashboardApiService
 */
class DashboardRemoteDataSource(private val dashboardApiService: DashboardEndpointInterface) :
    BaseDataSource() {
    /**
     * get all filled in surveys from a change initiative with a given id
     *
     * @param changeInitiativeId
     */
    suspend fun getFilledInSurveys(changeInitiativeId: Int) =
        getResult { dashboardApiService.getFilledInSurveys(changeInitiativeId) }

    /**
     * get the mood of a change initiative with a given id
     *
     * @param changeInitiativeId
     */
    suspend fun getMood(changeInitiativeId: Int) =
        getResult { dashboardApiService.getMood(changeInitiativeId) }
}
