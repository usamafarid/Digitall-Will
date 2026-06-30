package com.example.digitalwill.view

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalwill.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.AndroidViewModel
import com.example.digitalwill.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential


@SuppressLint("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun Login(   onNavigate: () -> Unit) {
   // val auth = FirebaseAuth.getInstance()
   val authViewModel = viewModel<AuthViewModel>()
    val context = LocalContext.current
    val credentialManager = androidx.credentials.CredentialManager.create(context)

    val googleIdOption = GetGoogleIdOption
        .Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId("57559936279-5i53mcola6tai4i2n03s33r7mj49l0d8.apps.googleusercontent.com")
        .setAutoSelectEnabled(true)
        .build()
        //"57559936279-5i53mcola6tai4i2n03s33r7mj49l0d8.apps.googleusercontent.com"

    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val getCredentialRequest = GetCredentialRequest
        .Builder()
        .addCredentialOption(googleIdOption)
        .build()
//    LaunchedEffect(Unit) {
//        authViewModel.getToken(getCredentialRequest)
//        GetGoogleIdOption("57559936279-5i53mcola6tai4i2n03s33r7mj49l0d8.apps.googleusercontent.com")
//        onNavigate()
//    }
    LaunchedEffect(Unit) { this
     //   CoroutineScope(Dispatchers.IO).launch {
      //      authViewModel.login(email,password)
            authViewModel.loginSuccess.collect(
                collector = {
                    if (authViewModel.loginSuccess.value){
                    onNavigate()
                        Log.d("Login", "Login success: ${authViewModel.loginSuccess.value}")

                }
                }
            )
//            if (authViewModel.loginSuccess.value){
//                onNavigate()
//            }

           // Log.d("Login", "Login success: ${authViewModel.loginSuccess.value}")
          //  onNavigate()
    //    }
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Title
        Text(
            text = "Welcome",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Secure Your Legacy Easily",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Name Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "NAME",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Enter Your Name", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    unfocusedContainerColor = Color(0xFFF1F1F1),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Email Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "EMAIL",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter Your Email Address @gmail.com", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    unfocusedContainerColor = Color(0xFFF1F1F1),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Password Field
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "PASSWORD",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Enter Your Password", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF1F1F1),
                    unfocusedContainerColor = Color(0xFFF1F1F1),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "or", color = Color.Gray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Google Sign In
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onNavigate() }
        ) {
            // Placeholder for Google Icon
            Surface(
                modifier = Modifier.size(30.dp),
                color = Color.White,
                shape = RoundedCornerShape(4.dp),
                shadowElevation = 1.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google), // Placeholder image
                        contentDescription = "Logo",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Sign in with Google",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable{
                    scope.launch {

                        try {
                            val request=credentialManager.getCredential(context,getCredentialRequest)
                            if (request.credential is CustomCredential){
                                val token = GoogleIdTokenCredential.createFrom(request.credential.data)
                                authViewModel.idToken(token.idToken)
                            }

                                this
                                //   CoroutineScope(Dispatchers.IO).launch {
                                //      authViewModel.login(email,password)
//                                authViewModel.loginSuccess.collect(
//                                    collector = {
//                                        if (authViewModel.loginSuccess.value) {
//                                            onNavigate()
//                                            Log.d(
//                                                "Login" ,
//                                                "Login success: ${authViewModel.loginSuccess.value}"
//                                            )
//
//                                        }
//                                    }
//                                )

                       //     onNavigate()
                        }
                        catch (e: Exception){
                       //     Toast.makeText(context , e.message ?: "Unknown Error" , Toast.LENGTH_SHORT).show()
                            Log.e("Error", e.message ?: "Unknown Error")
                        }


                    }
                  //  getCredentialRequest.credentialOptions
                  //  googleIdOption.serverClientId

                }


            )
        }

        Spacer(modifier = Modifier.height(30.dp))

//        // Terms and Policy
//        val annotatedText = buildAnnotatedString {
//            withStyle(style = SpanStyle(color = Color.Gray)) {
//                append("By continuing you agree to\n")
//            }
//            withStyle(style = SpanStyle(color = Color(0xFFF08080), fontWeight = FontWeight.Bold)) {
//                append("Terms of Service & Privacy Policy")
//            }
//        }
//        Text(
//            text = annotatedText,
//            textAlign = TextAlign.Center,
//            fontSize = 14.sp,
//            lineHeight = 20.sp
//        )

        Spacer(modifier = Modifier.weight(1f))

        // Login Button
        Button(
            onClick = {
                authViewModel.login(email,password)
                //onNavigate()


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        )
        {
            Text(
                text = "Log in",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    Login(onNavigate = {})


}
