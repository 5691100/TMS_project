package com.sales.android.projecttms.ui.contactslist

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentContactListBinding
import com.sales.android.projecttms.model.ContactData
import com.sales.android.projecttms.ui.buildingcontact.BuildingsWithContactsFragment
import com.sales.android.projecttms.ui.contactslist.adapter.ContactsListAdapter
import com.sales.android.projecttms.ui.editcontactinfo.EditContactInfoFragment
import com.sales.android.projecttms.utils.mapToArrayListContactsList
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListFragment : Fragment() {

    private val viewModel: ContactListViewModel by viewModels()

    private var binding: FragmentContactListBinding? = null

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granded ->
        if (granded) {
            viewModel.requiredHousehold.observe(viewLifecycleOwner) {
                val intent = Intent(Intent.ACTION_CALL)
                val phone = it?.contact?.phoneNumber
                intent.data = Uri.parse("tel:$phone")
                startActivity(intent)
            }
        } else {
            Toast.makeText(requireContext(), "Permission denied!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val buildingId = getInt("BuildingId")
            viewModel.getHouseholdsByBuildingId(buildingId)
        }

        viewModel.requiredBuilding.observe(viewLifecycleOwner) { building ->
            if (building != null) {
                binding?.householdsTitle?.text =
                    "${building.buildingStreet} ${building.houseNumber} ${building.houseCorpus}"
                setList(building.houseHoldsList.mapToArrayListContactsList())
                arguments?.apply {
                    val numberHH = getInt("numberHHtoScroll")
                    binding?.buildingsRecyclerView?.scrollToPosition(numberHH - 4)
                }
            }
        }

        binding?.returnToBuildings?.setOnClickListener {
            parentFragmentManager.replaceFragment(R.id.container2, BuildingsWithContactsFragment(), true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setList(list: List<ContactData>) {
        binding?.buildingsRecyclerView?.run {
            if (adapter == null) {
                adapter = ContactsListAdapter { contact, view ->
                    showPopup(contact, view)
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
            (adapter as? ContactsListAdapter)?.submitList(list)
            adapter?.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun showPopup(contact: ContactData, v: View) {
        val popup = PopupMenu(requireContext(), v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu_popup_contacts, popup.menu)
        viewModel.getHouseholdByBuildingIdAndNumberHH(contact.buildingID, contact.numberHH)
        popup.setForceShowIcon(true)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.makeCall -> {
                    try {
                        launcher.launch(android.Manifest.permission.CALL_PHONE)
                    } catch (e: Exception) {
                        Log.e("Exception", e.toString())
                    }
                }
                R.id.editContact -> {
                    parentFragmentManager.replaceWithAnimation(
                        R.id.container2,
                        EditContactInfoFragment().apply {
                            arguments = Bundle().apply {
                                putInt("BuildingId", contact.buildingID)
                                putInt("HouseholdNumber", contact.numberHH)
                            }
                        }
                    )
                }
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }
}
