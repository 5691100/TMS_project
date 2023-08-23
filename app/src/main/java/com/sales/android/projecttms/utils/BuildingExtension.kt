package com.sales.android.projecttms.utils

import com.sales.android.projecttms.model.BuildingData
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