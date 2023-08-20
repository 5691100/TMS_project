package com.sales.android.projecttms.ui.addcontactinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentAddContactInfoBinding
import com.sales.android.projecttms.model.StatusOfHousehold
import com.sales.android.projecttms.ui.householdslist.HouseholdListViewModel
import dagger.hilt.android.AndroidEntryPoint

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

//            viewModel.getHouseholdByBuildingIdAndNumberHH(buildingId, householdNumber)
        }

        binding?.run {
            saveButton.setOnClickListener {
                viewModel.requiredHousehold.observe(viewLifecycleOwner) { household ->
                    if (household != null) {
                        chipGroupProviderFixed.setOnCheckedStateChangeListener { group, checkedIds ->
                            var providerFixed = ""
                            when (group.checkedChipId) {
                                R.id.chipBTK -> providerFixed = "БTK"
                                R.id.chipMTS -> providerFixed = "МТС"
                                R.id.chipUnet -> providerFixed = "Unet"
                                R.id.chipAmigo -> providerFixed = "Amigo"
                                R.id.chipCosmos -> providerFixed = "Cosmos"
                                R.id.chipOther -> providerFixed = "Другой"
                            }
                            household.contact.fixedProvider = providerFixed
                        }
                        providerMobileChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                            var providerMobile = ""
                            when (group.checkedChipId) {
                                R.id.chipMobileA1 -> providerMobile = "A1"
                                R.id.chipMobileMTS -> providerMobile = "МТС"
                                R.id.chipMobileLife -> providerMobile = "Life"
                            }
                            household.contact.mobileProvider = providerMobile
                        }
                        totalPriceChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                            var totalPrice = 0
                            when (group.checkedChipId) {
                                R.id.chip40BYN -> totalPrice = 40
                                R.id.chip50BYN -> totalPrice = 50
                                R.id.chip60BYN -> totalPrice = 60
                                R.id.chip70BYN -> totalPrice = 70
                            }
                            household.contact.totalPayment = totalPrice
                        }
                        household.contact.name = binding?.nameInputEditText?.text.toString().trim()
                        household.contact.phoneNumber =
                            binding?.phoneInputEditText?.text.toString().trim()
                        household.statusOfHouseHold = StatusOfHousehold.THINKING.status
                        household.openStatus = true
                        household.reasonForStatus = ""
                        household.contact.comments =
                            binding?.commentsInputEditText?.text.toString().trim()
                        viewModel.setHouseholdToFirebase(household)
                    }
                }
            }
            parentFragmentManager.popBackStack()
        }
    }
}