package com.sales.android.projecttms.ui.sellerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentNavigationManagerBinding
import com.sales.android.projecttms.ui.profilemanagers.ProfileManagersFragment
import com.sales.android.projecttms.utils.replaceWithAnimation

class NavigationManagerFragment: Fragment() {

    private var binding: FragmentNavigationManagerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavigationManagerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.replaceWithAnimation(R.id.container3, SellersListFragment())

        binding?.bottomNavigationManagerView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.sellers -> {
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container3,
                        SellersListFragment()
                    )
                    return@setOnItemSelectedListener true
                }
                R.id.profileManager -> {
                    parentFragmentManager.replaceWithAnimation(R.id.container3, ProfileManagersFragment())
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener true
            }
        }
    }
}