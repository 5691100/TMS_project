package com.sales.android.projecttms.db

import androidx.room.*
import com.sales.android.projecttms.model.entity.BuildingEntity

@Dao
interface BuildingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBuildings(building: BuildingEntity)

    @Update
    suspend fun updateBuilding(building: BuildingEntity)

    @Query("SELECT * FROM Building")
    suspend fun getAllBuildings(): List<BuildingEntity>

    @Query("SELECT * FROM Building WHERE buildingId = :buildingId")
    suspend fun getRequiredBuilding(buildingId: Int): BuildingEntity
}