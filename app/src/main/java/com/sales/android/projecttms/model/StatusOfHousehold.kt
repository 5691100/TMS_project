package com.sales.android.projecttms.model

enum class StatusOfHousehold (val status: String) {
    NOT_OPEN ("Не открыта"),
    THINKING ("Думает"),
    REFUSE_BEFORE_PRES ("Отказ до презентации"),
    REFUSE_AFTER_PRES ("Отказ после презентации"),
    CONTRACT ("Договор")
}