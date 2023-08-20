package com.sales.android.projecttms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.sales.android.projecttms.ui.buildingslist.BuildingListFragment
import com.sales.android.projecttms.ui.buildingslist.NavigationFragment
import com.sales.android.projecttms.utils.replaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.replaceFragment(R.id.container, NavigationFragment(), true)
    }
}