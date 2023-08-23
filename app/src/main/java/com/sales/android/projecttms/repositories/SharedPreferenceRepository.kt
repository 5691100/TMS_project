package com.sales.android.projecttms.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val SHARED_PREF_FILE = "sharedPrefFile"
private const val USER_PREF_FILE = "userPrefFile"
private const val IS_FIRST_OPEN = "isFirstOpen"
private const val USER_FIRST_NAME = "userFirstName"
private const val USER_SECOND_NAME = "userSecondName"
private const val USER_ID = "userId"

@Singleton
class SharedPreferenceRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private var sharedPreferences: SharedPreferences
    private var userPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        userPreferences = context.getSharedPreferences(USER_PREF_FILE, Context.MODE_PRIVATE)
    }

    fun saveUser(userFirstName: String, userSecondName: String, userId: Int) {
        userPreferences.edit {
            putString(USER_FIRST_NAME, userFirstName)
            putString(USER_SECOND_NAME, userSecondName)
            putInt(USER_ID, userId)
        }
    }

    fun getUserName(): String? {
        return userPreferences.getString(USER_FIRST_NAME+""+ USER_SECOND_NAME, null)
    }

    fun getUserId(): Int? {
        return userPreferences.getInt(USER_ID, 0)
    }

}