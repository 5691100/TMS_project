package com.sales.android.projecttms.model

data class HouseholdData(
    var buildingID: Int = 0,
    var connectedStatus: Boolean = false,
    var contact: ContactData = ContactData(),
    var numberHH: Int = 0,
    var openStatus: Boolean = false,
    var reasonForStatus: String = "",
    var stageOfRefusal: String = "",
    var statusOfHouseHold: String = ""
)