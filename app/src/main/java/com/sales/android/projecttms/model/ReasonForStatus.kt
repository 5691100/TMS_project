package com.sales.android.projecttms.model

enum class ReasonForStatus (val reason: String) {
    NOT_AT_HOME ("Нет дома"),
    NO_BELL ("Нет звонка"),
    NOT_OPEN ("Не открыли"),
    THINKING ("Думают"),
    OLDS ("Пенсионеры"),
    NOT_LISTEN ("Не стали слушать"),
    REVIEWS ("Отзывы"),
    CABLE ("Провода"),
    COSTLY ("Дорого"),
    OBLIGATIONS ("Привязка/акция"),
    NOT_WANT ("Лень заниматься"),
    LATER ("Зайти позже")
}