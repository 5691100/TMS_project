package com.sales.android.projecttms.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.sales.android.projecttms.repositories.MockRepository
import javax.inject.Inject

class MyWorkerFactory @Inject constructor (private val mockRepository: MockRepository) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        // This only handles a single Worker, please don’t do this!!
        // See below for a better way using DelegatingWorkerFactory
        return UpdateFirebaseWorker(appContext, workerParameters, mockRepository)

    }
}