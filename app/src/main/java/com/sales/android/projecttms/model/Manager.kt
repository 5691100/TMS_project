package com.sales.android.projecttms.model

data class Manager(
    var firstName: String = "",
    var lastName: String = "",
    var managerId: Int = 0,
    var sellersUidList: ArrayList<String> = arrayListOf()
)
