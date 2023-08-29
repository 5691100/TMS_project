package com.sales.android.projecttms.ui.buildingcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentBuildingsWithContactsBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.ui.buildingcontact.adapter.BWCAdapter
import com.sales.android.projecttms.ui.buildingslist.adapter.BuildingListAdapter
import com.sales.android.projecttms.ui.contactslist.ContactListFragment
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildingsWithContactsFragment: Fragment() {

    private val viewModel: BuildingsWithContactsViewModel by viewModels()
    private var binding: FragmentBuildingsWithContactsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingsWithContactsBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.run {
            buildingListFB.observe(viewLifecycleOwner) {
                setList(it)
            }
        }
    }

    private fun setList(list: ArrayList<BuildingData>) {
        binding?.buildingsWithContactsRecyclerView?.run {
            if (adapter == null) {
                adapter = BWCAdapter { building ->
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container2,
                        ContactListFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", building.buildingID)
                                putInt("numberHHtoScroll", 0)
                            }
                        }
                    )

                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? BWCAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
    }
}