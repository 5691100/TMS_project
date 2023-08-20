package com.sales.android.projecttms.repositories

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginFBRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (error: Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
            } else {
                task.exception?.let { onError(it) }
            }
        }
    }

    fun isUserLogin() = auth.currentUser != null

    fun logOut() {
        auth.signOut()
    }
}