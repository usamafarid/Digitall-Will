package com.example.digitalwill.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow


class AuthViewModel(application : Application) : AndroidViewModel(application) {
    val auth= FirebaseAuth.getInstance()

//    val context = LocalContext

    //Gemini logic
//    private val auth = try {
//        FirebaseAuth.getInstance()
//    } catch (e: Exception) {
//        Log.e("","Failed to initialize Firebase Auth: ${e.message}")
//        Log.e(TAG , ":${e.message} " , e)
//        null
//    }
   var loginSuccess= MutableStateFlow(false)

        val sharedPreferences = application.getSharedPreferences("SHARED_PREFERENCE", Context.MODE_PRIVATE)


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
                    _user.value=auth?.currentUser
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
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    // Login successful

                    Log.d("AuthViewModel", "Success")
                    loginSuccess.value = true
                    _user.value=auth?.currentUser
                } else {
                    // Login failed
                    Log.d("AuthViewModel", "Login failed: ${it.exception?.message}")
                }
            }
    }
    @SuppressLint("CommitPrefEdits")
    fun saveUserPIN(pin: String){
        sharedPreferences.edit {
            putString("user_pin",pin)
            apply()
        }
    }
    fun getUserPIN(): String? {
        return sharedPreferences.getString("user_pin" , "")

    }
}
