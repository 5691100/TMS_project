package com.sales.android.projecttms.ui.addcontactinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sales.android.projecttms.R
import com.sales.android.projecttms.databinding.FragmentAddContactInfoBinding
import com.sales.android.projecttms.model.StatusOfHousehold
import com.sales.android.projecttms.ui.householdslist.HouseholdListFragment
import com.sales.android.projecttms.ui.householdslist.HouseholdListViewModel
import com.sales.android.projecttms.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

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

        binding?.run {
            chipGroupProviderFixed.setOnCheckedStateChangeListener { group, _ ->
                when (group.checkedChipId) {
                    R.id.chipBTK -> providerFixed = "БTK"
                    R.id.chipMTS -> providerFixed = "МТС"
                    R.id.chipUnet -> providerFixed = "Unet"
                    R.id.chipAmigo -> providerFixed = "Amigo"
                    R.id.chipCosmos -> providerFixed = "Cosmos"
                    R.id.chipOther -> providerFixed = "Другой"
                }
            }
            providerMobileChipGroup.setOnCheckedStateChangeListener { group, _ ->
                when (group.checkedChipId) {
                    R.id.chipMobileA1 -> providerMobile = "A1"
                    R.id.chipMobileMTS -> providerMobile = "МТС"
                    R.id.chipMobileLife -> providerMobile = "Life"
                }
            }
            totalPriceChipGroup.setOnCheckedStateChangeListener { group, _ ->
                when (group.checkedChipId) {
                    R.id.chip40BYN -> totalPrice = 40
                    R.id.chip50BYN -> totalPrice = 50
                    R.id.chip60BYN -> totalPrice = 60
                    R.id.chip70BYN -> totalPrice = 70
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
                        household.statusOfHouseHold = StatusOfHousehold.THINKING.status
                        household.openStatus = true
                        household.reasonForStatus = ""
                        household.contact.comments =
                            binding?.commentsInputEditText?.text.toString().trim()
                        viewModel.setHouseholdToFirebase(household)
                        Toast.makeText(requireContext(), "Контакт сохранен", Toast.LENGTH_LONG)
                            .show()
                        parentFragmentManager.replaceFragment(
                            R.id.container,
                            HouseholdListFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("numberHHtoScroll", household.numberHH)
                                    putInt("BuildingId", household.buildingID)
                                }
                            }, false
                        )
                    }
                }
            }
        }
    }
}