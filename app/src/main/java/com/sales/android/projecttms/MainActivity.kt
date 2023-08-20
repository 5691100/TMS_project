package com.sales.android.projecttms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.sales.android.projecttms.repositories.LoginFBRepository
import com.sales.android.projecttms.ui.buildingslist.BuildingListFragment
import com.sales.android.projecttms.ui.buildingslist.NavigationFragment
import com.sales.android.projecttms.ui.login.LoginFragment
import com.sales.android.projecttms.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var loginFBRepository: LoginFBRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}