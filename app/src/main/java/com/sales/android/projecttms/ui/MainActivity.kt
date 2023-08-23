package com.sales.android.projecttms.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.sales.android.projecttms.R
import com.sales.android.projecttms.receiver.NetworkReceiver
import com.sales.android.projecttms.repositories.LoginFBRepository
import com.sales.android.projecttms.repositories.NetworkStatusRepository
import com.sales.android.projecttms.ui.buildingslist.NavigationFragment
import com.sales.android.projecttms.ui.login.LoginFragment
import com.sales.android.projecttms.utils.getNetworkStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var loginFBRepository: LoginFBRepository

    @Inject
    lateinit var networkStatusRepository: NetworkStatusRepository

    private val receiver = NetworkReceiver()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        }
        registerReceiver(receiver, filter)
        networkStatusRepository.updateNetworkStatus(this.getNetworkStatus())
        lifecycleScope.launch {
            networkStatusRepository.getNetworkState()
                .filter { !it }
                .collectLatest {
                    Toast.makeText(
                        this@MainActivity, "no internet connection", Toast.LENGTH_LONG
                    )
                        .show()
                }
        }
        if (loginFBRepository.isUserLogin()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, NavigationFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment())
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}