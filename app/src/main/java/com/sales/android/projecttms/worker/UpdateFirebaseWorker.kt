package com.sales.android.projecttms.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sales.android.projecttms.repositories.MockRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltWorker
class UpdateFirebaseWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val mockRepository: MockRepository
) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        GlobalScope.launch {
            mockRepository.updateFirebase()
            Log.e("UpdateFirebaseWorker", "done")
        }
        return Result.success()
    }
}