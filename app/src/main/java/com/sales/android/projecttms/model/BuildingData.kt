package com.sales.android.projecttms.model

data class BuildingData(
    var userId: Int = 0,
    var buildingID: Int = 0,
    var buildingStreet: String = "",
    var houseNumber: Int = 0,
    var houseCorpus: String = "",
    var connectedHH: Int = 0,
    var contracts: Int = 0,
    var houseHoldsList: ArrayList<HouseholdData> = arrayListOf(),
    var openHH: Int = 0,
    var totalHH: Int = 0
)