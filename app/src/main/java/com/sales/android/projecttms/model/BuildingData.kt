package com.sales.android.projecttms.model

data class BuildingData(
    var userId: Int = 0,
    var buildingID: Int = 0,
    var buildingStreet: String = "",
    var houseNumber: Int = 0,
    var houseCorpus: String = "",
    var houseHoldsList: ArrayList<HouseholdData> = arrayListOf(),
    var totalHH: Int = 0
)