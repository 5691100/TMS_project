package com.sales.android.projecttms.utils

import com.sales.android.projecttms.model.BuildingData
import com.sales.android.projecttms.model.ContactData
import com.sales.android.projecttms.model.HouseholdData
import com.sales.android.projecttms.model.entity.BuildingEntity

fun BuildingData.mapToBuildingEntity(): BuildingEntity {
    return BuildingEntity(
        userId,
        buildingID,
        buildingStreet,
        houseNumber,
        houseCorpus,
        houseHoldsList,
        totalHH
    )
}

fun BuildingEntity.mapToBuildingData(): BuildingData {
    return BuildingData(
        userId,
        buildingID,
        buildingStreet,
        houseNumber,
        houseCorpus,
        houseHoldsList,
        totalHH
    )
}

fun List<BuildingEntity>.mapToBuildingDataArrayList(): ArrayList<BuildingData> {
    return map {
        it.mapToBuildingData()
    } as ArrayList<BuildingData>
}

fun ArrayList<HouseholdData>.mapToArrayListContactsList(): ArrayList<ContactData> {
    val list = ArrayList<ContactData>()
    forEach {
        if (it.contact.phoneNumber!="") list.add(it.contact)
    }
//    map {
//        it.contact
//    } as ArrayList<ContactData>
    return list
}