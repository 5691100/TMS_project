package com.sales.android.projecttms.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentProfileBinding
import com.sales.android.projecttms.repositories.LoginFBRepository
import com.sales.android.projecttms.repositories.SharedPreferenceRepository
import com.sales.android.projecttms.ui.login.LoginFragment
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    @Inject
    lateinit var loginFBRepository: LoginFBRepository

    private var binding: FragmentProfileBinding? = null

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getName()

        viewModel.name.observe(viewLifecycleOwner) {
            binding?.name?.text = it
        }

        binding?.logoutButton?.setOnClickListener{
            parentFragmentManager.replaceWithAnimation(R.id.container, LoginFragment())
            loginFBRepository.logOut()
        }
    }
}