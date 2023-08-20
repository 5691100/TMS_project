package com.sales.android.projecttms.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sales.android.projecttms.model.HouseholdData

class HouseholdTypeConverter {

    @TypeConverter
    fun listHouseholds(list: ArrayList<HouseholdData>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(json: String): ArrayList<HouseholdData> {
        val listType = object : TypeToken<ArrayList<HouseholdData>>() {}.type
        return Gson().fromJson(json, listType)
    }
}