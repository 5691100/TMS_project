package com.sales.android.projecttms.ui.addcontactinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sales.android.projecttms.databinding.FragmentAddContactInfoBinding
import com.sales.android.projecttms.databinding.FragmentContactListBinding

class AddContactInfoFragment: Fragment() {

    private var binding: FragmentAddContactInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactInfoBinding.inflate(inflater,container,false)
        return binding?.root
    }
}