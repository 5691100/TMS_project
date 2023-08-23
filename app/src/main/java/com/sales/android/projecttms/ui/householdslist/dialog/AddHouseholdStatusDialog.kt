package com.sales.android.projecttms.ui.householdslist.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentAddHouseholdStatusBinding
import com.sales.android.projecttms.model.ReasonForStatus
import com.sales.android.projecttms.model.StatusOfHousehold
import com.sales.android.projecttms.ui.addcontactinfo.AddContactInfoFragment
import com.sales.android.projecttms.ui.householdslist.HouseholdListViewModel
import com.sales.android.projecttms.utils.addFragment
import com.sales.android.projecttms.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddHouseholdStatusDialog : BottomSheetDialogFragment() {

    private val viewModel: HouseholdListViewModel by viewModels()

    private var binding: FragmentAddHouseholdStatusBinding? = null

    var onDismiss: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("BuildingID") { _, bundle ->
            val buildingID = bundle.getInt("bundleKey1")

            setFragmentResultListener("HouseholdNumber") { _, bundle ->
                val householdNumber = bundle.getInt("bundleKey2")
                viewModel.getHouseholdByBuildingIdAndNumberHH(buildingID, householdNumber)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddHouseholdStatusBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {

            chipGroupNotOpen.setOnCheckedStateChangeListener { group, checkedIds ->
                var reasonForStatus = ""
                when (group.checkedChipId) {
                    R.id.chipNotAtHome -> {
                        reasonForStatus = ReasonForStatus.NOT_AT_HOME.reason
                        binding?.chipNotAtHome?.isChecked = true
                    }
                    R.id.chipNoBell -> {
                        reasonForStatus = ReasonForStatus.NO_BELL.reason
                        binding?.chipNoBell?.isChecked = true
                    }
                    R.id.chipNotOpen -> {
                        reasonForStatus = ReasonForStatus.NOT_OPEN.reason
                        binding?.chipNotOpen?.isChecked = true
                    }
                }
                viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
                    if (household != null) {
                        household.openStatus = false
                        household.statusOfHouseHold = StatusOfHousehold.NOT_OPEN.status
                        household.reasonForStatus = reasonForStatus
                        viewModel.setHouseholdToFirebase(household)
                    }
                }
                onDismiss?.invoke()
                dismiss()
            }

            chipGroupRefuseBeforePres.setOnCheckedStateChangeListener { group, checkedIds ->
                var reasonForStatus = ""
                when (group.checkedChipId) {
                    R.id.chipOlds -> {
                        reasonForStatus = ReasonForStatus.OLDS.reason
                    }
                    R.id.chipNotListen -> {
                        reasonForStatus = ReasonForStatus.NOT_LISTEN.reason
                    }
                }
                viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
                    if (household != null) {
                        household.openStatus = true
                        household.statusOfHouseHold = StatusOfHousehold.REFUSE_BEFORE_PRES.status
                        household.reasonForStatus = reasonForStatus
                        viewModel.setHouseholdToFirebase(household)
                    }
                }
                onDismiss?.invoke()
                dismiss()
            }

            chipGroupRefuseAfterPres.setOnCheckedStateChangeListener { group, checkedIds ->
                var reasonForStatus = ""
                when (group.checkedChipId) {
                    R.id.chipBadReviews -> {
                        reasonForStatus = ReasonForStatus.REVIEWS.reason
                    }
                    R.id.chipFiber -> {
                        reasonForStatus = ReasonForStatus.CABLE.reason
                    }
                    R.id.chipCostly -> {
                        reasonForStatus = ReasonForStatus.COSTLY.reason
                    }
                    R.id.chipObligations -> {
                        reasonForStatus = ReasonForStatus.OBLIGATIONS.reason
                    }
                    R.id.chipNotWant -> {
                        reasonForStatus = ReasonForStatus.NOT_WANT.reason
                    }
                }
                viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
                    if (household != null) {
                        household.openStatus = true
                        household.statusOfHouseHold = StatusOfHousehold.REFUSE_AFTER_PRES.status
                        household.reasonForStatus = reasonForStatus
                        viewModel.setHouseholdToFirebase(household)
                    }
                }
                onDismiss?.invoke()
                dismiss()
            }

            addContactButton.setOnClickListener {
                viewModel.requiredHousehold.observe(viewLifecycleOwner) {
                    if (it != null) {
                        parentFragmentManager.replaceFragment(
                            R.id.container,
                            AddContactInfoFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("BuildingIdd", it.buildingID)
                                    putInt("HouseholdNumberr", it.numberHH)
                                }
                            },
                            true
                        )
                    }
                }
                dismiss()
            }
        }
    }
}