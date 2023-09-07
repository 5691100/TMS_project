package com.sales.android.projecttms.ui.householdslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.repositories.*
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
    private val buildingFirebaseRepository: BuildingFirebaseRepository,
    private val sellersRepository: SellersRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {

    var requiredHousehold = MutableLiveData<HouseholdData?>()
    var requiredBuilding = MutableLiveData<BuildingData?>()
    var householdList = MutableLiveData<ArrayList<HouseholdData>>()


    fun getBuildingByBuildingId(buildingID: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            networkStatusRepository.getNetworkState().collectLatest { status ->
                if (status) {
                    getBuildingUseCase.getBuildingsFromFB().collectLatest { buildings ->
                        if (buildings.isNotEmpty()) {
                            val building = buildings.find { it.buildingID == buildingID }
                            if (building != null) {
                                requiredBuilding.postValue(building)
                                householdList.postValue(building.houseHoldsList)
                            }
                        }
                    }
                } else {
                    requiredBuilding.postValue(getBuildingUseCase.getRequiredBuildingFromData(buildingID))
                    householdList.postValue(getBuildingUseCase.getRequiredBuildingFromData(buildingID).houseHoldsList)
                }
            }
        }
    }

    fun getHouseholdByBuildingIdAndNumberHH(buildingID: Int, householdNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            networkStatusRepository.getNetworkState().collectLatest { status ->
                if (status) {
                    getBuildingUseCase.getBuildingsFromFB().collectLatest { buildings ->
                        if (buildings.isNotEmpty()) {
                            val building = buildings.find { it.buildingID == buildingID }
                            if (building != null) {
                                requiredBuilding.postValue(building)
                                requiredHousehold.postValue(building.houseHoldsList.find { it.numberHH == householdNumber })
                            }
                        }
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
                    buildingFirebaseRepository.setHouseholdToBuildingFirebase(household)
                } else {
                    val building =
                        getBuildingUseCase.getRequiredBuildingFromData(household.buildingID)
                    building.houseHoldsList[household.numberHH - 1] = household
                    getBuildingUseCase.updateBuilding(building)
                }
            }
        }
    }

    fun updateSellerStatus(isOnWork: Int) {
        sellersRepository.setSellerStatusToSellerFirebase(isOnWork)
        sharedPreferenceRepository.updateStatus(isOnWork)
    }
}