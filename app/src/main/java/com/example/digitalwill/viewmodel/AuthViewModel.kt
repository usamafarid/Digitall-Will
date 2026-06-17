package com.example.digitalwill.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()
   var loginSuccess= MutableStateFlow(false)

    //Stateflow
    private var _user = mutableStateOf(auth.currentUser)
    val user get() = _user


    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Login successful

                    Log.d("AuthViewModel", "Success")
                    loginSuccess.value = true
                    _user.value=auth.currentUser
                } else {
                    // Login failed
                    Log.d("AuthViewModel", "Login failed: ${it.exception?.message}")
                }
            }

    }
//    fun getToken(idTokenCredential : GetCredentialRequest) {
//        GoogleAuthProvider.getCredential(idTokenCredential.toString() , null)
//            .let { auth.signInWithCredential(it) }
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    // Login successful
//                    Log.d("AuthViewModel" , "Success")
//                    loginSuccess.value = true
//                    _user.value = auth.currentUser
//
//                }
//            }
//    }
    fun idToken(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Login successful

                    Log.d("AuthViewModel", "Success")
                    loginSuccess.value = true
                    _user.value=auth.currentUser
                } else {
                    // Login failed
                    Log.d("AuthViewModel", "Login failed: ${it.exception?.message}")
                }
            }
    }
}
