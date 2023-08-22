package com.sales.android.projecttms.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sales.android.projecttms.model.HouseholdData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HouseholdFirebaseRepository @Inject constructor(
    private val database: DatabaseReference
) {

    val listHouseholds = MutableStateFlow(arrayListOf<HouseholdData>())

    fun getHousehold(buildingID: Int) {

        Firebase.database.reference.child("New1").child((buildingID - 1).toString())
            .child("houseHoldsList")
            .addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    val building = snapshot.getValue(BuildingData::class.java)
//                    val newList = listBuildings.value.apply {
//                        building?.let { add(it) }
//                    }
//                    GlobalScope.launch {
//                        listBuildings.emit(newList)
//                    }
                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val household = snapshot.getValue(HouseholdData::class.java)
                    val newHouseholdList = listHouseholds.value
                    newHouseholdList.replaceAll {
                        when {
                            it.numberHH == household?.numberHH -> {
                                household
                            }
                            else -> {
                                it
                            }
                        }
                    }
//                    listHouseholds.value.apply {
//                        remove(oldHousehold)
//                        household?.let { add(it.numberHH - 1, it) }
//                    }.forEach {
//                        newHouseholdList.add(it.copy())
//                    }
                    GlobalScope.launch {
                        listHouseholds.emit(arrayListOf())
                        listHouseholds.emit(newHouseholdList)
                    }

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
//                    val building = snapshot.getValue(BuildingData::class.java)
//                    val oldBuilding =
//                        listBuildings.value.find { it.buildingID == building?.buildingID }
//                    val newList = listBuildings.value.apply {
//                        remove(oldBuilding)
//                    }
//                    GlobalScope.launch {
//                        listBuildings.emit(newList)
//                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        Firebase.database.reference.child("New1").child((buildingID - 1).toString())
            .child("houseHoldsList").get().addOnSuccessListener {
                val list = arrayListOf<HouseholdData>()
                for (ds in it.children) {
                    try {
                        Log.e("list1", list.toString())
                        val household = ds.getValue(HouseholdData::class.java)
                        if (household != null) {
                            list.add(household)
                        }
                    } catch (e: Exception) {
                        Log.e("error", "error")
                    }
                }
                GlobalScope.launch {
                    listHouseholds.emit(list)
                }
            }
    }

    fun setHouseholdToFirebase(household: HouseholdData) {
        Firebase.database.reference.child("New1").child((household.buildingID - 1).toString())
            .child("houseHoldsList").child((household.numberHH - 1).toString()).setValue(household)
            .addOnSuccessListener {
                Log.i("firebase", "Got value")
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data")
            }
    }
}