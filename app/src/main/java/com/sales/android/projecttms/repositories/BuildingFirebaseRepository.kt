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
import com.sales.android.projecttms.model.BuildingData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BuildingFirebaseRepository @Inject constructor(
    private val database: DatabaseReference
) {

    val listBuildings = MutableStateFlow(arrayListOf<BuildingData>())

    init {

        Firebase.database.reference.child("New1")
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
                    val building = snapshot.getValue(BuildingData::class.java)
                    val newList: ArrayList<BuildingData> = listBuildings.value
                    newList.replaceAll {
                        when {
                            it.buildingID == building?.buildingID -> {
                                building
                            }else -> {
                            it
                        }
                        }
                    }
//                    listBuildings.value.apply {
//                        remove(oldBuilding)
//                        building?.let { add(it) }
//                    }.forEach {
//                        newList.add(it.copy())
//                    }
                    GlobalScope.launch {
                        listBuildings.emit(arrayListOf())
                        listBuildings.emit(newList)
                    }

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val building = snapshot.getValue(BuildingData::class.java)
                    val oldBuilding =
                        listBuildings.value.find { it.buildingID == building?.buildingID }
                    val newList = listBuildings.value.apply {
                        remove(oldBuilding)
                    }
                    GlobalScope.launch {
                        listBuildings.emit(newList)
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        Firebase.database.reference.child("New1").get().addOnSuccessListener {
            val list = arrayListOf<BuildingData>()
            for (ds in it.children) {
                try {
                    Log.e("list1", list.toString())
                    val building = ds.getValue(BuildingData::class.java)
                    if (building != null) {
                        list.add(building)
                    }
                } catch (e: Exception) {
                    Log.e("error", "error")
                }
            }
            GlobalScope.launch {
                listBuildings.emit(list)
            }
        }
    }
}