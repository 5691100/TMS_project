package com.sales.android.projecttms.model

data class ContactData(
    var buildingID: Int = 0,
    var comments: String = "",
    var dateOfNextContact: String = "",
    var fixedProvider: String = "",
    var mobileProvider: String = "",
    var name: String = "",
    var numberHH: Int = 0,
    var phoneNumber: String = "",
    var reasonForRefusal: String = "",
    var statusOfContact: String = "",
    var totalPayment: Int = 0
)