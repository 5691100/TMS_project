package com.sales.android.projecttms.usecase

import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.repositories.BuildingDatabaseRepository
import com.sales.android.projecttms.repositories.BuildingFirebaseRepository
import com.sales.android.projecttms.utils.mapToBuildingData
import com.sales.android.projecttms.utils.mapToBuildingDataArrayList
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class GetBuildingUseCase @Inject constructor(
    private val buildingFirebaseRepository: BuildingFirebaseRepository,
    private val buildingDatabaseRepository: BuildingDatabaseRepository
) {
    suspend fun getBuildingsFromFB() = channelFlow<ArrayList<BuildingData>> {
        buildingFirebaseRepository.listBuildings.collectLatest {
            if (it.isNotEmpty()) {
                it.forEach { building ->
                    buildingDatabaseRepository.saveBuilding(building)
                }
                send(it)
            }
        }
    }

    suspend fun getBuildingsFromData() =
        buildingDatabaseRepository.getAllBuildings().mapToBuildingDataArrayList()

    suspend fun getRequiredBuildingFromData(buildingId: Int) =
        buildingDatabaseRepository.getRequiredBuilding(buildingId).mapToBuildingData()

    suspend fun updateBuilding(building: BuildingData) {
        buildingDatabaseRepository.updateBuilding(building)
    }
}