package com.sales.android.projecttms.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sales.android.projecttms.repositories.SharedPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository
): ViewModel() {

    val name = MutableLiveData<String?>()

    fun getName() {
        val nameUser = sharedPreferenceRepository.getUserName()
        name.postValue(nameUser)
    }
}