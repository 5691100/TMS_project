package com.sales.android.projecttms.ui.buildingslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentBuildingListBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.ui.buildingslist.adapter.BuildingListAdapter
import com.sales.android.projecttms.ui.householdslist.HouseholdListFragment
import com.sales.android.projecttms.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuildingListFragment: Fragment() {

    private val viewModel: BuildingListViewModel by viewModels()
    private var binding: FragmentBuildingListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingListBinding.inflate(inflater,container,false)
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
        binding?.buildingsRecyclerView?.run {
            if (adapter == null) {
                adapter = BuildingListAdapter { buildingID, view ->
                    showPopup(buildingID, view)
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? BuildingListAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
    }

    private fun showPopup(buildingID: Int, v: View) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_popup, popup.menu)
        popup.setOnMenuItemClickListener { it ->
            when (it.itemId) {
                R.id.startWork -> {
                    showStartWorkDialog(buildingID)
                }
                R.id.showHH -> {
                    showStartShowDialog(buildingID)
                }
                R.id.showContacts -> {
                }
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    private fun showStartWorkDialog(buildingID: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Начать работу по дому?")
            .setPositiveButton("Да") { _, _ ->
                var date: Long? = null
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date of start work")
                        .build()
                datePicker.isCancelable = false
                datePicker.show(parentFragmentManager, "tag");
                datePicker.addOnPositiveButtonClickListener {
                    date = it
                }
                datePicker.addOnNegativeButtonClickListener {
                    parentFragmentManager.popBackStack()
                }

                parentFragmentManager.replaceFragment(
                    R.id.container,
                    HouseholdListFragment(),
                    true
                )
                setFragmentResult("DateOfWork", bundleOf("bundleKey" to date))
                setFragmentResult("Id", bundleOf("bundleKey" to buildingID))
            }
            .setNegativeButton("Нет") { _, _ ->
            }
            .show()
    }

    private fun showStartShowDialog(buildingID: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Просмотреть квартиры?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.buildingListFB.observe(viewLifecycleOwner) {
                    parentFragmentManager.replaceFragment(
                        R.id.container,
                        HouseholdListFragment().apply {
                            arguments =Bundle().apply {
                                putInt("BuildingId", buildingID)
                                putString("Street", it.find { it.buildingID == buildingID }?.buildingStreet)
                                putString("BuildingNumber", it.find { it.buildingID == buildingID }?.houseNumber.toString())
                                putString("BuildingCorpus", it.find { it.buildingID == buildingID }?.houseCorpus)
                            }
                        },
                        true
                    )
                }
                setFragmentResult("Id", bundleOf("bundleKey" to buildingID))
            }
            .setNegativeButton("Нет") { _, _ ->
            }
            .show()
    }
}