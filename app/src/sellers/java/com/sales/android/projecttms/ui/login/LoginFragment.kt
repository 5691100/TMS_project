package com.sales.android.projecttms.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentLoginBinding
import com.sales.android.projecttms.ui.buildingslist.NavigationFragment
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "Authentication"

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            openBuildingList = {
                parentFragmentManager.replaceWithAnimation(
                    R.id.container,
                    NavigationFragment()
                )
            }
            error.observe(viewLifecycleOwner) {
                binding?.errorTextView?.run {
                    text = it
                    visibility = View.VISIBLE
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                binding?.progressView?.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
        binding?.loginButton?.setOnClickListener {
            viewModel.login(
                binding?.emailEditText?.text.toString(),
                binding?.passwordEditText?.text.toString()
            )
        }
    }
}