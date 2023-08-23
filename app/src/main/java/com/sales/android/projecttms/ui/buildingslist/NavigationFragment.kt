package com.sales.android.projecttms.ui.buildingslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentNavigationBinding
import com.sales.android.projecttms.ui.contactslist.ContactListFragment
import com.sales.android.projecttms.ui.results.ResultsFragment
import com.sales.android.projecttms.utils.replaceFragment

class NavigationFragment: Fragment() {

    private var binding: FragmentNavigationBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.replaceFragment(R.id.container2, BuildingListFragment(), true)

        binding?.bottomNavigationView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.buildings -> {
                    parentFragmentManager.replaceFragment(
                        R.id.container2,
                        BuildingListFragment(),
                        true
                    )
                    return@setOnItemSelectedListener true
                }
                R.id.contacts -> {
                    parentFragmentManager.replaceFragment(R.id.container2, ContactListFragment(), true)
                    return@setOnItemSelectedListener true
                }
                R.id.results -> {
                    parentFragmentManager.replaceFragment(R.id.container2, ResultsFragment(), true)
                    return@setOnItemSelectedListener true
                }
//                R.id.menu -> {
//                    parentFragmentManager.replaceFragment(R.id.container2, MenuFragment(), true)
//                    return@setOnItemSelectedListener true
//                }
                else -> return@setOnItemSelectedListener true
            }
        }
    }
}