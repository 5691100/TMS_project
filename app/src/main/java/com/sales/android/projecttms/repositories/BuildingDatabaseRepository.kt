package com.sales.android.projecttms.repositories

import com.sales.android.projecttms.db.BuildingDao
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.utils.mapToBuildingEntity
import javax.inject.Inject

class BuildingDatabaseRepository @Inject constructor(
    private val buildingDao: BuildingDao,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) {

    suspend fun saveBuilding(building: BuildingData) {
        buildingDao.saveBuildings(building.mapToBuildingEntity())
    }

    suspend fun updateBuilding(building: BuildingData) {
        buildingDao.updateBuilding(building.mapToBuildingEntity())
    }

    suspend fun getAllBuildings() = buildingDao.getAllBuildings(sharedPreferenceRepository.getUserId())

    suspend fun getRequiredBuilding(buildingId: Int) = buildingDao.getRequiredBuilding(buildingId)
}