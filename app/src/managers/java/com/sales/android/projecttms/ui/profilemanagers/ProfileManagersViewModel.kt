package com.sales.android.projecttms.ui.profilemanagers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sales.android.projecttms.repositories.SharedPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileManagersViewModel @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository
): ViewModel() {

    val name = MutableLiveData<String?>()

    fun getName() {
        val nameManager = sharedPreferenceRepository.getManagerName()
        name.postValue(nameManager)
    }
}