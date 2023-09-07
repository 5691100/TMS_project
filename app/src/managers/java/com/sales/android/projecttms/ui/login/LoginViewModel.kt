package com.sales.android.projecttms.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sales.android.projecttms.repositories.LoginFBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginFBRepository: LoginFBRepository
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()

    val error = MutableLiveData<String>()

    var openSellersList: (() -> Unit)? = null

    fun loginM(email: String, password: String) {
        isLoading.value = true
        loginFBRepository.loginM(email, password, {
            isLoading.value = false
            openSellersList?.invoke()
        }, {
            isLoading.value = false
            error.value = "Bad credentials"
        })
    }
}