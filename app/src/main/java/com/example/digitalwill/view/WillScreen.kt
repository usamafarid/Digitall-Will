package com.example.digitalwill.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WillScreen(navController: NavHostController, onBack: () -> Unit) {
    var selectedTab by remember { mutableStateOf(1) } // 1 for "Will" tab
    var showPopup by remember { mutableStateOf(false) }

    if (showPopup) {
        Dialog(onDismissRequest = { showPopup = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        "Beneficiaries",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.clickable {
                            showPopup = false
                            navController.navigate("beneficiaries")
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                    Text(
                        "Physical Assets",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.clickable {
                            showPopup = false
                            navController.navigate("add_asset")
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                    Text(
                        "Digital Assets",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.clickable {
                            showPopup = false
                            navController.navigate("add_asset")
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                    Text(
                        "Life Goals",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        modifier = Modifier.clickable {
                            showPopup = false
                            navController.navigate("life_goals")
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Will", fontWeight = FontWeight.Bold, fontSize = 28.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(32.dp))
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedTab ,
                onTabSelected = { selectedTab = it } ,
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showPopup = true },
                containerColor = Color.Black,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(30.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFEEEEEE))
                .padding(horizontal = 20.dp)
        ) {
            item {
                // Blockchain Status Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Blockchain Status",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                            Text("smart contract", fontSize = 12.sp)
                            Text("Network: Ethereum", fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color(0xFF4CAF50), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(Color.Red, CircleShape)
                                )
                            }
                            Icon(
                                Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            item { WillSectionHeader("Beneficiaries") }
            item { 
                Card(
                    onClick = { navController.navigate("beneficiaries") },
                    shape = RoundedCornerShape(16.dp),

                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        WillListItem("Ahmed Bhai")
                        WillListItem("Ali Beta")
                        WillListItem("Ayesha Beti")
                    }
                }
            }

            item { WillSectionHeader("Physical Assets") }
            items(listOf("Cash and Bank", "Real Estate", "Vehicles", "Valuables", "Investments")) { WillListItem(it) }

            item { WillSectionHeader("Digital Assets") }
            items(listOf("Bitcoin", "Ethereum", "Crypto wallet", "Online accounts")) { WillListItem(it) }

            item { WillSectionHeader("Life Goals") }
            item { WillListItem("Ali | PHD (NUST)") }
            item { WillListItem("Ayesha | MBBS") }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun WillSectionHeader(title: String) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            title,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(Color.Black)
        )
    }
}

@Composable
fun WillListItem(text: String) {
    Text(
        text,
        fontSize = 18.sp,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun WillScreenPreview() {
    val navController = rememberNavController()
    WillScreen(navController = navController, onBack = {})
}
