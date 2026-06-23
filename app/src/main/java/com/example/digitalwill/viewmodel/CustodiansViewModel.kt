package com.example.digitalwill.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustodiansViewModel: ViewModel() {

    //object of firestore
    val firebaseFirestore = FirebaseFirestore.getInstance()
        //object of firebase auth
    val firebaseAuth = FirebaseAuth.getInstance()
    //state management
    val custodian1Phone = mutableStateOf("")
    val relation1 = mutableStateOf("")
    val custodian2Phone = mutableStateOf("")
    val relation2 = mutableStateOf("")

    //function data save in firebase firestore
    fun saveCustodianToFirebase(){
        //object data class custodian
        val custodian1= Custodian(phone = custodian1Phone.value, relation = relation1.value)
        val custodian2= Custodian(phone = custodian2Phone.value, relation = relation2.value)
          //store unique id of users e.g a1b2c3d4e5....
        val userid = firebaseAuth.currentUser?.uid

        val collection = firebaseFirestore
            .collection("users")
            .document(userid!!)
            .collection("custodian")
//            .add(custodian1)

        collection.add(custodian1)
        collection.add(custodian2)

//        val collection2 = firebaseFirestore
//            .collection("users")
//            .document(userid!!)
//            .collection("custodian")
//            .add(custodian2)


    }
    data class Custodian(
        val phone: String = "",
        val relation: String =""
    )

}