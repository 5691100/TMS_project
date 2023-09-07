package com.sales.android.projecttms.ui.householdslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentHouseholdListBinding
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.ui.addhhstatus.AddHouseholdStatusFragment
import com.sales.android.projecttms.ui.buildingslist.NavigationFragment
import com.sales.android.projecttms.ui.householdslist.adapter.HouseholdListAdapter
import com.sales.android.projecttms.utils.replaceWithAnimation
import com.sales.android.projecttms.utils.replaceWithReverseAnimation
import com.trendyol.bubblescrollbarlib.BubbleTextProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HouseholdListFragment : Fragment() {

    private val viewModel: HouseholdListViewModel by viewModels()

    private var binding: FragmentHouseholdListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHouseholdListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val buildingId = getInt("BuildingId")
            viewModel.getBuildingByBuildingId(buildingId)
        }

        viewModel.requiredBuilding.observe(viewLifecycleOwner) { building ->
            if (building != null) {
                binding?.householdsTitle?.text =
                    "${building.buildingStreet} ${building.houseNumber} ${building.houseCorpus}"
                setList(building.houseHoldsList)
                arguments?.apply {
                    val numberHH = getInt("numberHHtoScroll")
                    binding?.buildingsRecyclerView?.scrollToPosition(numberHH - 4)
                }
            }
        }

        binding?.returnToBuildings?.setOnClickListener {
            viewModel.updateSellerStatus(0)
            parentFragmentManager.replaceWithReverseAnimation(R.id.container, NavigationFragment())
        }
    }


    private fun setList(list: List<HouseholdData>) {
        binding?.buildingsRecyclerView?.run {
            if (adapter == null) {
                adapter = HouseholdListAdapter { household ->
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container,
                        AddHouseholdStatusFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", household.buildingID)
                                putInt("householdNumber", household.numberHH)
                            }
                        }
                    )
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? HouseholdListAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
        binding?.bubbleScrollBar?.attachToRecyclerView(binding!!.buildingsRecyclerView)
        binding?.bubbleScrollBar?.bubbleTextProvider = BubbleTextProvider { list[it].numberHH.toString() }
    }
}