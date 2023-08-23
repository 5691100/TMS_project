package com.sales.android.projecttms.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sales.android.projecttms.databinding.FragmentContactListBinding

class ContactListFragment: Fragment() {

    private var binding: FragmentContactListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater,container,false)
        return binding?.root
    }

}