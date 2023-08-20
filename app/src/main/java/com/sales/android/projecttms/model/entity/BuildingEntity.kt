package com.sales.android.projecttms.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sales.android.projecttms.model.HouseholdData

@Entity(tableName = "Building")
data class BuildingEntity(
    @ColumnInfo("userId")
    var userId: Int,
    @PrimaryKey
    @ColumnInfo("buildingId")

    var buildingID: Int,
    @ColumnInfo("buildingStreet")
    var buildingStreet: String,
    @ColumnInfo("houseNumber")
    var houseNumber: Int,
    @ColumnInfo("houseCorpus")
    var houseCorpus: String,
    @ColumnInfo("connectedHH")
    var connectedHH: Int,
    @ColumnInfo("houseHoldsList")
    var houseHoldsList: ArrayList<HouseholdData>,
    @ColumnInfo("openHH")
    var openHH: Int,
    @ColumnInfo("totalHH")
    var totalHH: Int
)