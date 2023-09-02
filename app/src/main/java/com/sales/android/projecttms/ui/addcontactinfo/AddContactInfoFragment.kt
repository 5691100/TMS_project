package com.sales.android.projecttms.ui.addcontactinfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentAddContactInfoBinding
import com.sales.android.projecttms.model.ReasonForStatus
import com.sales.android.projecttms.model.StatusOfContact
import com.sales.android.projecttms.model.StatusOfHousehold
import com.sales.android.projecttms.ui.householdslist.HouseholdListFragment
import com.sales.android.projecttms.ui.householdslist.HouseholdListViewModel
import com.sales.android.projecttms.utils.convertLongToTime
import com.sales.android.projecttms.utils.replaceFragment
import com.sales.android.projecttms.utils.replaceWithAnimation
import com.sales.android.projecttms.utils.replaceWithReverseAnimation
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

const val COUNTRY_CODE = "+375"

@AndroidEntryPoint
class AddContactInfoFragment : Fragment() {

    private var binding: FragmentAddContactInfoBinding? = null

    private val viewModel: HouseholdListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactInfoBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val buildingId = getInt("BuildingIdd")
            val householdNumber = getInt("HouseholdNumberr")
            viewModel.getHouseholdByBuildingIdAndNumberHH(buildingId, householdNumber)
        }

        binding?.phoneInputEditText?.setText(COUNTRY_CODE)

        var providerFixed = ""
        var providerMobile = ""
        var totalPrice = 0
        var statusOfHousehold = StatusOfHousehold.THINKING.status
        var reasonForStatus = ""
        var statusOfContact = ""

        binding?.run {
            chipGroupProviderFixed.setOnCheckedStateChangeListener { group, _ ->
                providerFixed = when (group.checkedChipId) {
                    R.id.chipBTK -> "БTK"
                    R.id.chipMTS -> "МТС"
                    R.id.chipUnet -> "Unet"
                    R.id.chipAmigo -> "Amigo"
                    R.id.chipCosmos -> "Cosmos"
                    R.id.chipOther -> "Другой"
                    else -> ""
                }
            }
            providerMobileChipGroup.setOnCheckedStateChangeListener { group, _ ->
                providerMobile = when (group.checkedChipId) {
                    R.id.chipMobileA1 -> "A1"
                    R.id.chipMobileMTS -> "МТС"
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
            dateOfNextContactButton.setOnClickListener {
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
                        household.contact.statusOfContact = statusOfContact
                        household.contact.reasonForRefusal = reasonForStatus
                        household.contact.comments =
                            binding?.commentsInputEditText?.text.toString().trim()
                        household.contact.dateOfNextContact =
                            binding?.dateOfNextContactTextView?.text.toString().trim()
                        viewModel.setHouseholdToFirebase(household)
                        Toast.makeText(requireContext(), "Контакт сохранен", Toast.LENGTH_LONG)
                            .show()
                        parentFragmentManager.replaceWithReverseAnimation(
                            R.id.container,
                            HouseholdListFragment().apply {
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
                        R.id.container,
                        HouseholdListFragment().apply {
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