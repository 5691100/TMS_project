package com.sales.android.projecttms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.sales.android.projecttms.repositories.NetworkStatusRepository
import com.sales.android.projecttms.utils.getNetworkStatus
import com.sales.android.projecttms.worker.MyWorkerFactory
import com.sales.android.projecttms.worker.UpdateFirebaseWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkReceiver : BroadcastReceiver() {

    @Inject
    lateinit var networkStatusRepository: NetworkStatusRepository

    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        networkStatusRepository.updateNetworkStatus(context?.getNetworkStatus() ?: false)

        if (context != null) {
            if (context.getNetworkStatus()) {
                val workRequestBuilder =
                    OneTimeWorkRequest.Builder(UpdateFirebaseWorker::class.java).build()
                WorkManager.getInstance(context).enqueue(workRequestBuilder)
            }
        }
    }
}