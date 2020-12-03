package com.hogentessentials1.essentials.data.model.network

class ChangeManagerRemoteDataSource(val cmApiService: ChangeManagersEndpointInterface) : BaseDataSource() {

    suspend fun getChangeManagerById(changeManagerId: Int) = getResult { cmApiService.getChangeManagerById(changeManagerId) }

    suspend fun getChangeManagersFromOrganizationWithId(organizationId: Int) = getResult { cmApiService.getChangeManagersFromOrganizationWithId(organizationId) }

    suspend fun getChangeManagerByEmail(email: String) = getResult { cmApiService.getChangeManagerByEmail(email) }
}