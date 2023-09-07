package com.sales.android.projecttms.ui.profilemanagers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentProfileBinding
import com.sales.android.projecttms.databinding.FragmentProfileManagersBinding
import com.sales.android.projecttms.repositories.LoginFBRepository
import com.sales.android.projecttms.repositories.SharedPreferenceRepository
import com.sales.android.projecttms.ui.login.LoginFragment
import com.sales.android.projecttms.ui.profile.ProfileViewModel
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileManagersFragment : Fragment() {

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    @Inject
    lateinit var loginFBRepository: LoginFBRepository

    private var binding: FragmentProfileManagersBinding? = null

    private val viewModel: ProfileManagersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileManagersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getName()

        viewModel.name.observe(viewLifecycleOwner) {
            binding?.name?.text = it
        }

        binding?.logoutButton?.setOnClickListener {
            parentFragmentManager.replaceWithAnimation(R.id.container, LoginFragment())
            loginFBRepository.logOutM()
        }
    }
}
