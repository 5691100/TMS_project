package com.sales.android.projecttms.di

import android.content.Context
import androidx.room.Room
import com.sales.android.projecttms.db.AppDatabase
import com.sales.android.projecttms.db.BuildingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "buildingsDataBase"

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideBuildingDao (@ApplicationContext context: Context): BuildingDao {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build().getBuildingDao()
    }
}