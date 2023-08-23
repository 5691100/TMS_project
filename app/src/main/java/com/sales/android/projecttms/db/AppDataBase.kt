package com.sales.android.projecttms.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sales.android.projecttms.model.entity.BuildingEntity
import com.sales.android.projecttms.utils.HouseholdTypeConverter

@Database(entities = [BuildingEntity::class], version = 1)
@TypeConverters(HouseholdTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBuildingDao(): BuildingDao
}