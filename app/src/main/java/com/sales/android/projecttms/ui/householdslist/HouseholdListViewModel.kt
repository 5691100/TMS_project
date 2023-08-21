package com.sales.android.projecttms.ui.householdslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.repositories.HouseholdFirebaseRepository
import com.sales.android.projecttms.repositories.NetworkStatusRepository
import com.sales.android.projecttms.usecase.GetBuildingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseholdListViewModel @Inject constructor(
    private val getBuildingUseCase: GetBuildingUseCase,
    private val networkStatusRepository: NetworkStatusRepository,
    private val householdFirebaseRepository: HouseholdFirebaseRepository
) : ViewModel() {

    var requiredHousehold = MutableLiveData<HouseholdData?>()
    var requiredBuilding = MutableLiveData<BuildingData>()
    var householdList = MutableLiveData<ArrayList<HouseholdData>>()


    fun getHouseholdsByBuildingId(buildingID: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            networkStatusRepository.getNetworkState().collectLatest {
                if (it) {
                    householdFirebaseRepository.getHousehold(buildingID)
                    householdFirebaseRepository.listHouseholds.collect { householdsList ->
                        householdList.postValue(householdsList)
                    }
                } else {
                    val building = getBuildingUseCase.getRequiredBuildingFromData(buildingID)
                    householdList.postValue(building.houseHoldsList)
                }
            }
        }
    }

    fun getHouseholdByBuildingIdAndNumberHH(buildingID: Int, householdNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            networkStatusRepository.getNetworkState().collectLatest { status ->
                if (status) {
                    householdFirebaseRepository.getHousehold(buildingID)
                    householdFirebaseRepository.listHouseholds.collect { householdList ->
                        requiredHousehold.postValue(householdList.find { it.numberHH == householdNumber })
                    }
                } else {
                    val building = getBuildingUseCase.getRequiredBuildingFromData(buildingID)
                    requiredHousehold.postValue(building.houseHoldsList.find { it.numberHH == householdNumber })
                }
            }
        }
    }

    fun setHouseholdToFirebase(household: HouseholdData) {
        viewModelScope.launch(Dispatchers.IO) {
            networkStatusRepository.getNetworkState().collectLatest { status ->
                if (status) {
                    householdFirebaseRepository.setHouseholdToFirebase(household)
                } else {
                    val building = getBuildingUseCase.getRequiredBuildingFromData(household.buildingID)
                    building.houseHoldsList[household.numberHH-1] = household
                    getBuildingUseCase.updateBuilding(building)
                }
            }
        }
    }
}
