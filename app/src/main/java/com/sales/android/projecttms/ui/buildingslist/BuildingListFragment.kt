package com.sales.android.projecttms.ui.buildingslist

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentBuildingListBinding
import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.repositories.SharedPreferenceRepository
import com.sales.android.projecttms.ui.buildingslist.adapter.BuildingListAdapter
import com.sales.android.projecttms.ui.contactslist.ContactListFragment
import com.sales.android.projecttms.ui.householdslist.HouseholdListFragment
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BuildingListFragment : Fragment() {

    @Inject
    lateinit var sharedPreferenceRepository: SharedPreferenceRepository

    private val viewModel: BuildingListViewModel by viewModels()
    private var binding: FragmentBuildingListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.run {
            buildingListFB.observe(viewLifecycleOwner) {
                setList(it)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
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

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showPopup(buildingID: Int, v: View) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_popup, popup.menu)
        popup.setForceShowIcon(true)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.startWork -> {
                    showStartWorkDialog(buildingID)
                }
                R.id.showHH -> {
                    viewModel.buildingListFB.observe(viewLifecycleOwner) {
                        parentFragmentManager.replaceWithAnimation(
                            R.id.container,
                            HouseholdListFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("BuildingId", buildingID)
                                    putInt("numberHHtoScroll", 0)
                                }
                            }
                        )
                    }
                }
                R.id.showContacts -> {
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container2,
                        ContactListFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", buildingID)
                                putInt("numberHHtoScroll", 0)
                            }
                        }
                    )
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
                datePicker.show(parentFragmentManager, "tag")
                datePicker.addOnPositiveButtonClickListener {
                    date = it
                    viewModel.updateSellerStatus(1)
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container,
                        HouseholdListFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", buildingID)
                                putInt("numberHHtoScroll", 0)
                            }
                        }
                    )
                }
                datePicker.addOnNegativeButtonClickListener {

                }
            }
            .setNegativeButton("Нет") { _, _ ->
            }
            .show()
    }

    private fun showStartShowDialog(buildingID: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Просмотреть квартиры?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.buildingListFB.observe(/* owner = */ viewLifecycleOwner) {
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container,
                        HouseholdListFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", buildingID)
                                putInt("numberHHtoScroll", 0)
                            }
                        }
                    )
                }
            }
            .setNegativeButton("Нет") { _, _ ->
            }
            .show()
    }

    private fun showContactsListDialog(buildingID: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Просмотреть контакты по дому?")
            .setPositiveButton("Да") { _, _ ->
                viewModel.buildingListFB.observe(viewLifecycleOwner) {
                    parentFragmentManager.replaceFragment(
                        R.id.container,
                        ContactListFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", buildingID)
                                putInt("numberHHtoScroll", 0)
                            }
                        },
                        true
                    )
                }
            }
            .setNegativeButton("Нет") { _, _ ->
            }
            .show()
    }
}