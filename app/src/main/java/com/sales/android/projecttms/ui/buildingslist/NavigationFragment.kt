package com.sales.android.projecttms.ui.buildingslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentNavigationBinding
import com.sales.android.projecttms.ui.buildingcontact.BuildingsWithContactsFragment
import com.sales.android.projecttms.ui.contactslist.ContactListFragment
import com.sales.android.projecttms.ui.profile.ProfileFragment
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation

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
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container2,
                        BuildingListFragment()
                    )
                    return@setOnItemSelectedListener true
                }
                R.id.contacts -> {
                    parentFragmentManager.replaceWithAnimation(R.id.container2, BuildingsWithContactsFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.results -> {
                    parentFragmentManager.replaceWithAnimation(R.id.container2, ProfileFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener true
            }
        }
    }
}