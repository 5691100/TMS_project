package com.sales.android.projecttms.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.sales.android.projecttms.model.Manager
import com.sales.android.projecttms.model.Seller
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
                        val sellerFirebase = it.getValue(Seller::class.java)
                        if (sellerFirebase != null) {
                            sharedPreferenceRepository.saveUser(
                                sellerFirebase.firstName,
                                sellerFirebase.lastName,
                                sellerFirebase.userId,
                                sellerFirebase.work
                            )
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

    fun loginM(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (error: Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
                val manager = auth.currentUser
                manager?.let { manager ->
                    database.child("Managers").child(manager.uid).get().addOnSuccessListener {
                        val managerFirebase = it.getValue(Manager::class.java)
                        if (managerFirebase != null) {
                            sharedPreferenceRepository.saveManager(
                                managerFirebase.firstName,
                                managerFirebase.lastName,
                                managerFirebase.managerId,
                                managerFirebase.sellersUidList
                            )
                        }
                    }
                }
            } else {
                task.exception?.let { onError(it) }
            }
        }
    }

    fun isManagerLogin() = auth.currentUser != null

    fun logOutM() {
        auth.signOut()
    }
}