package com.sales.android.projecttms.ui.sellerslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sales.android.projecttms.model.Seller
import com.sales.android.projecttms.repositories.SellersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellersListViewModel @Inject constructor(
    private val sellersRepository: SellersRepository
): ViewModel() {

    var sellersList = MutableLiveData<ArrayList<Seller>>()

    fun getSellersFromFB() {
        viewModelScope.launch(Dispatchers.IO) {
            sellersRepository.listSeller.collectLatest {
                if (it.isNotEmpty()) {
                    sellersList.postValue(it)
                }
            }
        }
    }
}