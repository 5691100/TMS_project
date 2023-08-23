package com.sales.android.projecttms.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.sales.android.projecttms.model.User
import javax.inject.Inject

class LoginFBRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: DatabaseReference,
    private val sharedPreferenceRepository: SharedPreferenceRepository
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
                val user = auth.currentUser
                user?.let { user ->
                    database.child("Users").child(user.uid).get().addOnSuccessListener {
                        val userFirebase = it.getValue(User::class.java)
                        if (userFirebase != null) {
                            sharedPreferenceRepository.saveUser(userFirebase.firstName, userFirebase.lastName, userFirebase.userId )
                        }
                    }
                }
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