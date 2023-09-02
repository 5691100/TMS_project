package com.sales.android.projecttms.ui.editcontactinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentEditContactInfoBinding
import com.sales.android.projecttms.model.ReasonForStatus
import com.sales.android.projecttms.model.StatusOfContact
import com.sales.android.projecttms.model.StatusOfHousehold
import com.sales.android.projecttms.ui.addcontactinfo.COUNTRY_CODE
import com.sales.android.projecttms.ui.contactslist.ContactListFragment
import com.sales.android.projecttms.ui.contactslist.ContactListViewModel
import com.sales.android.projecttms.ui.householdslist.HouseholdListFragment
import com.sales.android.projecttms.utils.convertLongToTime
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import com.sales.android.projecttms.utils.replaceWithReverseAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditContactInfoFragment: Fragment() {

    private val viewModel: ContactListViewModel by viewModels()

    private var binding: FragmentEditContactInfoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditContactInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val buildingId = getInt("BuildingId")
            val householdNumber = getInt("HouseholdNumber")
            viewModel.getHouseholdByBuildingIdAndNumberHH(buildingId, householdNumber)
        }

        viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
            if (household!= null) {
                binding?.nameInputEditText?.setText(household.contact.name)
                binding?.phoneInputEditText?.setText(household.contact.phoneNumber)
                binding?.commentsInputEditText?.setText(household.contact.comments)
                binding?.dateOfNextContactTextView?.text = household.contact.dateOfNextContact
                when (household.contact.fixedProvider) {
                    "ÁTK" -> binding?.chipBTK?.isChecked = true
                    "ÌÒÑ" -> binding?.chipMTS?.isChecked = true
                    "Unet" -> binding?.chipUnet?.isChecked = true
                    "Amigo" -> binding?.chipAmigo?.isChecked = true
                    "Cosmos" -> binding?.chipCosmos?.isChecked = true
                    "Äðóãîé" -> binding?.chipOther?.isChecked = true
                }
                when (household.contact.mobileProvider) {
                    "A1" -> binding?.chipMobileA1?.isChecked = true
                    "ÌÒÑ" -> binding?.chipMobileMTS?.isChecked = true
                    "Life" -> binding?.chipMobileLife?.isChecked = true
                }
                when (household.contact.totalPayment) {
                    40 -> binding?.chip40BYN?.isChecked = true
                    50 -> binding?.chip50BYN?.isChecked = true
                    60 -> binding?.chip60BYN?.isChecked = true
                    70 -> binding?.chip70BYN?.isChecked = true
                }
            }
        }

        var providerFixed = ""
        var providerMobile = ""
        var totalPrice = 0
        var statusOfHousehold = StatusOfHousehold.THINKING.status
        var reasonForStatus = ""
        var statusOfContact = ""

        binding?.run {
            chipGroupProviderFixed.setOnCheckedStateChangeListener { group, _ ->
                providerFixed = when (group.checkedChipId) {
                    R.id.chipBTK -> "ÁTK"
                    R.id.chipMTS -> "ÌÒÑ"
                    R.id.chipUnet -> "Unet"
                    R.id.chipAmigo -> "Amigo"
                    R.id.chipCosmos -> "Cosmos"
                    R.id.chipOther -> "Äðóãîé"
                    else -> ""
                }
            }
            providerMobileChipGroup.setOnCheckedStateChangeListener { group, _ ->
                providerMobile = when (group.checkedChipId) {
                    R.id.chipMobileA1 -> "A1"
                    R.id.chipMobileMTS -> "ÌÒÑ"
                    R.id.chipMobileLife -> "Life"
                    else -> ""
                }
            }
            totalPriceChipGroup.setOnCheckedStateChangeListener { group, _ ->
                totalPrice = when (group.checkedChipId) {
                    R.id.chip40BYN -> 40
                    R.id.chip50BYN -> 50
                    R.id.chip60BYN -> 60
                    R.id.chip70BYN -> 70
                    else -> 0
                }
            }
            chipGroupRefuseAfterPres.setOnCheckedStateChangeListener { group, _ ->
                when (group.checkedChipId) {
                    R.id.chipBadReviews -> {
                        reasonForStatus = ReasonForStatus.REVIEWS.reason
                        statusOfHousehold = StatusOfHousehold.REFUSE_AFTER_PRES.status
                        statusOfContact = StatusOfContact.REFUSE.status
                    }
                    R.id.chipCostly -> {
                        reasonForStatus = ReasonForStatus.COSTLY.reason
                        statusOfHousehold = StatusOfHousehold.REFUSE_AFTER_PRES.status
                        statusOfContact = StatusOfContact.REFUSE.status
                    }
                    R.id.chipFiber -> {
                        reasonForStatus = ReasonForStatus.CABLE.reason
                        statusOfHousehold = StatusOfHousehold.REFUSE_AFTER_PRES.status
                        statusOfContact = StatusOfContact.REFUSE.status
                    }
                    R.id.chipObligations -> {
                        reasonForStatus = ReasonForStatus.OBLIGATIONS.reason
                        statusOfHousehold = StatusOfHousehold.REFUSE_AFTER_PRES.status
                        statusOfContact = StatusOfContact.REFUSE.status
                    }
                    R.id.chipNotWant -> {
                        reasonForStatus = ReasonForStatus.NOT_WANT.reason
                        statusOfHousehold = StatusOfHousehold.REFUSE_AFTER_PRES.status
                        statusOfContact = StatusOfContact.REFUSE.status
                    }
                    else -> {
                        reasonForStatus = ""
                        statusOfHousehold = StatusOfHousehold.THINKING.status
                        statusOfContact = StatusOfContact.THINKING.status
                    }
                }
            }
            dateOfNextContactButton.setOnClickListener{
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date of start work")
                        .build()
                datePicker.isCancelable = false
                datePicker.show(parentFragmentManager, "tag")
                datePicker.addOnPositiveButtonClickListener {
                    val date = convertLongToTime(it)
                    dateOfNextContactTextView.text = date ?: ""
                }
                datePicker.addOnNegativeButtonClickListener {

                }
            }
        }
        binding?.run {
            saveButton.setOnClickListener {
                viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
                    if (household != null) {
                        household.contact.name = binding?.nameInputEditText?.text.toString().trim()
                        household.contact.phoneNumber =
                            binding?.phoneInputEditText?.text.toString().trim()
                        household.contact.fixedProvider = providerFixed
                        household.contact.mobileProvider = providerMobile
                        household.contact.totalPayment = totalPrice
                        household.statusOfHouseHold = statusOfHousehold
                        household.openStatus = true
                        household.reasonForStatus = reasonForStatus
                        household.contact.reasonForRefusal = reasonForStatus
                        household.contact.statusOfContact = statusOfContact
                        household.contact.comments =
                            binding?.commentsInputEditText?.text.toString().trim()
                        household.contact.dateOfNextContact = binding?.dateOfNextContactTextView?.text.toString().trim()
                        viewModel.setHouseholdToFirebase(household)
                        Toast.makeText(requireContext(), "Êîíòàêò ñîõðàíåí", Toast.LENGTH_LONG)
                            .show()
                        parentFragmentManager.replaceWithReverseAnimation(
                            R.id.container2,
                            ContactListFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("numberHHtoScroll", household.numberHH)
                                    putInt("BuildingId", household.buildingID)
                                }
                            }
                        )
                    }
                }
            }
        }
        binding?.backButton?.setOnClickListener {
            viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
                if (household != null) {
                    parentFragmentManager.replaceWithReverseAnimation(
                        R.id.container2,
                        ContactListFragment().apply {
                            arguments = Bundle().apply {
                                putInt("numberHHtoScroll", household.numberHH)
                                putInt("BuildingId", household.buildingID)
                            }
                        }
                    )
                }
            }
        }
    }
}