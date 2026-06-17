package com.example.digitalwill.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustodiansViewModel: ViewModel() {

    val firebaseFirestore = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()
    val custodian1Phone = mutableStateOf("")
    val relation1 = mutableStateOf("")
    val custodian2Phone = mutableStateOf("")
    val relation2 = mutableStateOf("")




    fun saveCustodianToFirebase(){
        val userid = firebaseAuth.currentUser?.uid

        val collection = firebaseFirestore.collection("users")
    }
    data class Custodian(
        val phone: String? = null,
        val relation: String? =null
    )
}