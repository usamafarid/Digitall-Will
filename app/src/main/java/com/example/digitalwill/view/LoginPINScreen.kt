package com.example.digitalwill.view

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Lock
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalwill.R
import com.example.digitalwill.viewmodel.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun LoginPINScreen(onBack: () -> Unit, onUnlock: () -> Unit) {

    var pin by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authViewModel = viewModel<AuthViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Top Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create PIN",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Lock Icon
            Image(
                painter = painterResource(id = R.drawable.password),
                contentDescription = "Lock Icon",
                modifier = Modifier.size(80.dp),
//                tint = Color.Gray
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Enter your 4 Digit PIN",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))

            // PIN Indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(4) { index ->
                    PinIndicators(isFilled = index < pin.length)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Numeric Keypad
            NumericKeypads(
                onNumberClick = { number ->
                    if (pin.length < 4) {
                        pin += number
                    }
                },
                onDeleteClick = {
                    if (pin.isNotEmpty()) {
                        pin = pin.dropLast(1)
                    }
                },
                onClearClick = {
                    pin = ""
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // SAVE Button
            Button(
                onClick = {
                    if (pin.length == 4) {
                       // onUnlock()
                        // authViewModel.sharedPreferences.getString("user_pin" , pin)
                      val savePin= authViewModel.getUserPIN()
                        if (pin == savePin){
                          onUnlock()

                        }
                        else{
                            Toast.makeText(
                                context ,
                                "Wrong PIN,Please enter correct PIN" ,
                                Toast.LENGTH_SHORT
                            ).show()
                            pin=""
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    disabledContainerColor = Color.Black
                ),
                enabled = pin.length == 4
            ) {
                Text(
                    text = "Unlock",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

//            Text(
//                text = "Skip for now",
//                modifier = Modifier.clickable { onContinue() },
//                color = Color.Black,
//                fontSize = 16.sp,
//                textDecoration = TextDecoration.Underline
//            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun PinIndicators(isFilled: Boolean) {
    Surface(
        modifier = Modifier.size(50.dp),
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isFilled) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.Black , CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(Color.White.copy(alpha = 0.3f) , CircleShape)
                )
            }
        }
    }
}

@Composable
fun NumericKeypads(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onClearClick: () -> Unit
) {
    val numbers = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("<-", "0", "DEL")
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        numbers.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                row.forEach { key ->
                    KeypadButtons(
                        text = key,
                        onClick = {
                            when (key) {
                                "<-" -> onClearClick()
                                "DEL" -> onDeleteClick()
                                else -> onNumberClick(key)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun KeypadButtons(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .size(width = 60.dp , height = 60.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "spec:width=400dp,height=800dp")
@Composable
fun LoginPINScreenPreview() {
    LoginPINScreen(onBack = {}, onUnlock = {})
}
