package com.example.assignment3.firestore

import android.app.Activity
import android.util.Log
import com.example.assignment3.activities.DashboardAdminActivity
import com.example.assignment3.activities.DashboardUserActivity
import com.example.assignment3.activities.LoginActivity
import com.example.assignment3.activities.SignUpActivity
import com.example.assignment3.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    fun signupUser(activity: SignUpActivity,userInfo: User){

        mFireStore.collection("users")
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnFailureListener { e->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while Sign Up the user",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser!=null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity){
        mFireStore.collection("users")
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                val user = document.toObject(User::class.java)!!

                when (activity){
                    is LoginActivity -> activity.userLoggedInSuccess(user)
                    is DashboardUserActivity -> activity.getDashboard(user)
                    is DashboardAdminActivity -> activity.getDashboard(user)
                }
            }
    }
}