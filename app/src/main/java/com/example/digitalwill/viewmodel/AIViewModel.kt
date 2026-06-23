package com.example.digitalwill.viewmodel

import android.util.Log
import androidx.collection.mutableIntSetOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerateContentResponse
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AIViewModel() : ViewModel() {
  val model = Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel("gemini-3.5-flash")

    //UI state management
    private val _response= MutableStateFlow("")
    var response = _response.asStateFlow()

    //we want to use try-catch because in case of any error app not crash
    fun generateContent(prompt: String)  {
         viewModelScope.launch {
             try {
                 val prompt = prompt
                 val response=model.generateContent(prompt)
                 val reply=response.text
                 if (reply != null) {
                     _response.value=reply
                 }
             }catch (e: Exception){
                 Log.e("Erro","${e.message}")
             }


         }

    }

}


