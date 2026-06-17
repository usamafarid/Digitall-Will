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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsScreen(onBack: () -> Unit, onAddAsset: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    
    val physicalAssets = listOf(
        AssetItem("Cash & Bank", "HBL ACCOUNT - PKR 500,000", "💰"),
        AssetItem("Real Estate", "Home - Phase 6, DHA Lahore", "🏠"),
        AssetItem("Vehicles", "CAR/BIKES - PKR 500,000", "🚗"),
        AssetItem("Valuables", "Gold - PKR 500,000", "💎"),
        AssetItem("Investments", "Stocks/Crypto - PKR 500,000", "📈")
    )
    
    val digitalAssets = listOf(
        AssetItem("Bitcoin", "85 Lac", "₿"),
        AssetItem("Ethereum", "3 Lac", "🔹")
    )

    val filteredPhysical = physicalAssets.filter { 
        it.category.contains(searchQuery, ignoreCase = true) || it.name.contains(searchQuery, ignoreCase = true) 
    }
    val filteredDigital = digitalAssets.filter { 
        it.category.contains(searchQuery, ignoreCase = true) || it.name.contains(searchQuery, ignoreCase = true) 
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddAsset() },
                containerColor = Color.Black,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(70.dp).padding(bottom = 10.dp, end = 10.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(40.dp))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF)) // Light Gray Background
                .padding(innerPadding)
        ) {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(32.dp).clickable { onBack() },
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "My Assets & Wealth",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    // Search Bar
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black, modifier = Modifier.size(28.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Searchbar", color = Color.Gray, fontSize = 16.sp) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                ),
                                singleLine = true
                            )
                        }
                    }
                }

                if (filteredPhysical.isNotEmpty()) {
                    item {
                        Text(
                            text = "Physical Assets",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    items(filteredPhysical) { item ->
                        AssetCard(item)
                    }
                }

                if (filteredDigital.isNotEmpty()) {
                    item {
                        Text(
                            text = "Digital Assets",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    items(filteredDigital) { item ->
                        AssetCard(item)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp)) // Padding for FAB
                }
            }
        }
    }
}

@Composable
fun AssetCard(item: AssetItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon/Emoji Background
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = Color(0xFFF1F1F1)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(item.emoji, fontSize = 24.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = item.category,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

data class AssetItem(val category: String, val name: String, val emoji: String)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AssetsPreview() {
    AssetsScreen(onBack = {}, onAddAsset = {})
}
