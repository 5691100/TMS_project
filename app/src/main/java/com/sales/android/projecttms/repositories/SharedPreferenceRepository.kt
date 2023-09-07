package com.sales.android.projecttms.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val MANAGER_PREF_FILE = "managerPrefFile"
private const val MANAGER_FIRST_NAME = "managerFirstName"
private const val MANAGER_SECOND_NAME = "managerSecondName"
private const val MANAGER_ID = "managerId"
private const val SELLERS_UID_LIST = "sellersUidList"
private const val USER_PREF_FILE = "userPrefFile"
private const val USER_FIRST_NAME = "userFirstName"
private const val USER_SECOND_NAME = "userSecondName"
private const val USER_ID = "userId"
private const val USER_STATUS = "userStatus"


@Singleton
class SharedPreferenceRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private var managerPreferences: SharedPreferences
    private var userPreferences: SharedPreferences

    init {
        managerPreferences = context.getSharedPreferences(MANAGER_PREF_FILE, Context.MODE_PRIVATE)
        userPreferences = context.getSharedPreferences(USER_PREF_FILE, Context.MODE_PRIVATE)
    }

    fun saveUser(userFirstName: String, userSecondName: String, userId: Int, userStatus: Int) {
        userPreferences.edit {
            putString(USER_FIRST_NAME, userFirstName)
            putString(USER_SECOND_NAME, userSecondName)
            putInt(USER_ID, userId)
            putInt(USER_STATUS, userStatus)
        }
    }

    fun updateStatus(isOnWork: Int) {
        userPreferences.edit {
            putInt(USER_STATUS, isOnWork)
        }
    }

    fun getStatus(): Int {
        return userPreferences.getInt(USER_STATUS, 0)
    }

    fun getUserName(): String? {
        val firstName = userPreferences.getString(USER_FIRST_NAME, null)
        val lastName = userPreferences.getString(USER_SECOND_NAME, null)

        return "$firstName $lastName"
    }

    fun getUserId(): Int? {
        return userPreferences.getInt(USER_ID, 0)
    }

    fun saveManager(
        mangerFirstName: String,
        managerSecondName: String,
        managerId: Int,
        sellersUidList: ArrayList<String>
    ) {
        managerPreferences.edit {
            putString(MANAGER_FIRST_NAME, mangerFirstName)
            putString(MANAGER_SECOND_NAME, managerSecondName)
            putInt(MANAGER_ID, managerId)
            putStringSet(SELLERS_UID_LIST, sellersUidList.toSet())
        }
    }

    fun getManagerName(): String? {
        val firstName = managerPreferences.getString(MANAGER_FIRST_NAME, null)
        val lastName = managerPreferences.getString(MANAGER_SECOND_NAME, null)

        return "$firstName $lastName"
    }

}