package com.sales.android.projecttms.ui.buildingslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.usecase.GetBuildingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingListViewModel @Inject constructor(
    private val getBuildingUseCase: GetBuildingUseCase
) : ViewModel() {

    var buildingListFB = MutableLiveData<ArrayList<BuildingData>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBuildingUseCase.getBuildingsFromFB().collectLatest {
                if (it.isNotEmpty()) {
                    buildingListFB.postValue(it)
                }
            }
        }
    }
}