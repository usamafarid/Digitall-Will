package com.example.digitalwill.view

import android.app.Notification
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalwill.viewmodel.CustodiansViewModel
import androidx.core.net.toUri

@Composable
fun CustodiansScreen(onNavigate: () -> Unit) {

    val custodiansViewModel: CustodiansViewModel = viewModel()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light Gray Background
            .padding(36.dp)
    )

    {
        // Top Bar: Title
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Custodians",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
//            Text(
//                text = "Select",
//                fontSize = 28.sp,
//                fontWeight = FontWeight.Bold,
//                color = Color.Black
//            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Custodian 1 Input
        CustodianItemInput(
            label = "Custodian 1",
            phoneValue = custodiansViewModel.custodian1Phone.value,
            onPhoneChange = { custodiansViewModel.custodian1Phone.value = it },
            relationValue = custodiansViewModel.relation1.value,
            onRelationChange = { custodiansViewModel.relation1.value = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Custodian 2 Input
        CustodianItemInput(
            label = "Custodian 2",
            phoneValue = custodiansViewModel.custodian2Phone.value,
            onPhoneChange = { custodiansViewModel.custodian2Phone.value = it },
            relationValue = custodiansViewModel.relation2.value,
            onRelationChange = { custodiansViewModel.relation2.value = it }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Custom SMS Invitation Button with 3D Shadow
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp)
        ) {
            // Shadow Layer (Black Box)
            Box(
                modifier = Modifier
                    .offset(x = 4.dp, y = 4.dp)
                    .width(280.dp)
                    .height(65.dp)
                    .background(Color.Black, shape = CircleShape)
            )
            // Button Layer (White Box)
            Button(
                onClick = {
                    custodiansViewModel.saveCustodianToFirebase()

                    val intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = "smsto:${custodiansViewModel.custodian1Phone.value},${custodiansViewModel.custodian2Phone.value}".toUri()
                    intent.putExtra("sms_body","Hi, I've selected you as my custodian on Digital Will app. Please download it here: [It available o play store]")
                    context.startActivity(intent)

                    onNavigate() },
                modifier = Modifier
                    .width(280.dp)
                    .height(65.dp)
                    .border(2.dp, Color.Black, CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "SMS INVITATION",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustodianItemInput(
    label: String,
    phoneValue: String,
    onPhoneChange: (String) -> Unit,
    relationValue: String,
    onRelationChange: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) } // Dropdown khulne ke liye
    val relations = listOf("Brother", "Sister", "Father", "Mother", "Son", "Daughter", "Friend")

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // 1. Phone Number Input Field
                OutlinedTextField(
                    value = phoneValue,
                    onValueChange = onPhoneChange,
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 2. Relation Dropdown Menu
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = !isExpanded }
                ) {
                    OutlinedTextField(
                        value = if (relationValue.isEmpty()) "Select Relation" else relationValue,
                        onValueChange = {},
                        readOnly = true, // User khud na likh sake
                        label = { Text("Relation") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = Color.Black
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        relations.forEach { relation ->
                            DropdownMenuItem(
                                text = { Text(relation) },
                                onClick = {
                                    onRelationChange(relation)
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustodiansPreview() {
    CustodiansScreen(onNavigate = {})
}
