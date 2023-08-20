package com.sales.android.projecttms.ui.householdslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sales.android.projecttms.databinding.FragmentHouseholdListBinding
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.ui.householdslist.adapter.HouseholdListAdapter
import com.sales.android.projecttms.ui.householdslist.dialog.AddHouseholdStatusDialog
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
            val buildingStreet = getString("Street")
            val houseNumber = getString("BuildingNumber")
            val houseCorpus = getString("BuildingCorpus")

            binding?.householdsTitle?.text =
                "$buildingStreet $houseNumber $houseCorpus"
            viewModel.getHouseholdsByBuildingId(buildingId)
        }

        viewModel.householdList.observe(viewLifecycleOwner) { householdList ->
            setList(householdList)
        }

        viewModel.getBuildingsFromData()
    }


    private fun setList(list: List<HouseholdData>) {
        binding?.buildingsRecyclerView?.run {
            if (adapter == null) {
                adapter = HouseholdListAdapter { household ->
                    showAddStatusDialog(household).apply {
                        arguments = Bundle().apply {
                            putInt("BuildingId", household.buildingID)
                            putInt("householdNumber", household.numberHH)
                        }
                    }
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? HouseholdListAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun showAddStatusDialog(household: HouseholdData) {

        val buildingId = household.buildingID
        val householdNumber = household.numberHH

        setFragmentResult("BuildingID", bundleOf("bundleKey1" to buildingId))
        setFragmentResult("HouseholdNumber", bundleOf("bundleKey2" to householdNumber))

        AddHouseholdStatusDialog().apply {
            onDismiss = {
                Toast.makeText(requireContext(), "Status edited!", Toast.LENGTH_LONG).show()
            }
        }.show(parentFragmentManager, "Add status")
    }
}