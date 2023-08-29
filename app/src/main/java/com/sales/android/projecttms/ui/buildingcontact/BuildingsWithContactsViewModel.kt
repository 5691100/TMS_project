package com.sales.android.projecttms.ui.buildingcontact

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.repositories.NetworkStatusRepository
import com.sales.android.projecttms.usecase.GetBuildingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingsWithContactsViewModel @Inject constructor(
    private val networkStatusRepository: NetworkStatusRepository,
    private val getBuildingUseCase: GetBuildingUseCase
): ViewModel() {

    var buildingListFB = MutableLiveData<ArrayList<BuildingData>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            networkStatusRepository.getNetworkState().collectLatest {
                if (it) {
                    getBuildingUseCase.getBuildingsFromFB().collectLatest { buildings ->
                        if (buildings.isNotEmpty()) {
                            buildingListFB.postValue(buildings)
                        }
                    }
                } else {
                    buildingListFB.postValue(getBuildingUseCase.getBuildingsFromData())
                }
            }
        }
    }
}