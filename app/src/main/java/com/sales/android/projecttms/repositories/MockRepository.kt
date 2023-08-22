package com.sales.android.projecttms.repositories

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class MockRepository @Inject constructor(
    private val buildingDatabaseRepository: BuildingDatabaseRepository
) {
    suspend fun updateFirebase() {
        val buildings = buildingDatabaseRepository.getAllBuildings()
        buildings.forEach{
            Firebase.database.reference.child("New1").child((it.buildingID-1).toString()).setValue(it)
        }
    }
}