package com.sales.android.projecttms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.sales.android.projecttms.repositories.NetworkStatusRepository
import com.sales.android.projecttms.utils.getNetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkReceiver : BroadcastReceiver() {

    @Inject
    lateinit var networkStatusRepository: NetworkStatusRepository

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        networkStatusRepository.updateNetworkStatus(context?.getNetworkStatus() ?: false)
    }
}