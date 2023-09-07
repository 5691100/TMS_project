package com.sales.android.projecttms.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sales.android.projecttms.model.Seller
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SellersRepository @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository,
    private val auth: FirebaseAuth,
    private val buildingDatabaseRepository: BuildingDatabaseRepository
) {

    val listSeller = MutableStateFlow(arrayListOf<Seller>())


    init {

        Firebase.database.reference.child("Users")
            .addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                }

                @RequiresApi(Build.VERSION_CODES.N)
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val seller = snapshot.getValue(Seller::class.java)
                    val newList: ArrayList<Seller> = listSeller.value
                    newList.replaceAll {
                        when {
                            it.userId == seller?.userId -> {
                                seller

                            }
                            else -> {
                                it
                            }
                        }
                    }

                    GlobalScope.launch {
                        listSeller.emit(arrayListOf())
                        listSeller.emit(newList)
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
        Firebase.database.reference.child("Users").get().addOnSuccessListener {
            val list = arrayListOf<Seller>()
            for (ds in it.children) {
                try {
                    Log.e("list2", list.toString())
                    val seller = ds.getValue(Seller::class.java)
                    val userId = sharedPreferenceRepository.getUserId()
                    if (seller != null) {
                        list.add(seller)
//                        GlobalScope.launch {
//                            buildingDatabaseRepository.saveBuilding(building)
//                        }
                    }
                } catch (e: Exception) {
                    Log.e("error", "error")
                }
            }
            GlobalScope.launch {
                listSeller.emit(list)
            }
        }
    }

    fun setSellerStatusToSellerFirebase(isOnWork: Int) {
        val currentSeller = auth.currentUser
        if (currentSeller != null) {
            Firebase.database.reference.child("Users").child(currentSeller.uid).child("work")
                .setValue(isOnWork)
                .addOnSuccessListener {
                    Log.e("firebase", "Got value")
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data")
                }
        }
    }
}