package com.sales.android.projecttms.ui.sellerslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentSellersListBinding
import com.sales.android.projecttms.model.Seller
import com.sales.android.projecttms.repositories.SharedPreferenceRepository
import com.sales.android.projecttms.ui.buildingslist.NavigationFragment
import com.sales.android.projecttms.ui.sellerslist.adapter.SellersListAdapter
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SellersListFragment : Fragment() {

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private val viewModel: SellersListViewModel by viewModels()

    private var binding: FragmentSellersListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSellersFromFB()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellersListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            viewModel.getSellersFromFB()
            viewModel.sellersList.observe(viewLifecycleOwner) {
                setList(it)
            }
            binding!!.swipeRefreshLayout.isRefreshing = false
        }

//        viewModel.getSellersFromFB()
        viewModel.sellersList.observe(viewLifecycleOwner) {
            setList(it)
        }
    }

    private fun setList(list: ArrayList<Seller>) {
        binding?.sellersRecyclerView?.run {
            if (adapter == null) {
                adapter = SellersListAdapter { seller ->
                    sharedPreferenceRepository.saveUser(seller.firstName, seller.lastName, seller.userId, seller.work)
                    parentFragmentManager.replaceWithAnimation(R.id.container, NavigationFragment())
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? SellersListAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
    }
}