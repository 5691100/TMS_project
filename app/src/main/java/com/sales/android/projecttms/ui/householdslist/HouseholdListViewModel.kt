package com.sales.android.projecttms.ui.householdslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.repositories.HouseholdFirebaseRepository
import com.sales.android.projecttms.usecase.GetBuildingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseholdListViewModel @Inject constructor(
    private val getBuildingUseCase: GetBuildingUseCase,
    private val householdFirebaseRepository: HouseholdFirebaseRepository
) : ViewModel() {

    var requiredHousehold = MutableLiveData<HouseholdData?>()
    var requiredBuilding = MutableLiveData<BuildingData>()
    var householdList = MutableLiveData<ArrayList<HouseholdData>>()


    fun getHouseholdsByBuildingId(buildingID: Int) {
        householdFirebaseRepository.getHousehold(buildingID)
        viewModelScope.launch(Dispatchers.IO) {
            householdFirebaseRepository.listHouseholds.collect { householdsList ->
                householdList.postValue(householdsList)
            }
        }
    }

    fun getHouseholdByBuildingIdAndNumberHH(buildingID: Int, householdNumber: Int) {
        householdFirebaseRepository.getHousehold(buildingID)
        viewModelScope.launch(Dispatchers.IO) {
            householdFirebaseRepository.listHouseholds.collect { householdList ->
                requiredHousehold.postValue(householdList.find { it.numberHH == householdNumber })
            }
        }
    }

    fun setHouseholdToFirebase(household: HouseholdData) {
        viewModelScope.launch(Dispatchers.IO) {
            householdFirebaseRepository.setHouseholdToFirebase(household)
        }
    }

    fun getBuildingsFromData() {
        viewModelScope.launch (Dispatchers.IO) {
            val newList = getBuildingUseCase.getBuildingsFromData()
            newList.toString()
        }
    }
}