package com.example.digitalwill.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text("Digital Will", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Dashboard,
                        contentDescription = "Logo",
                        modifier = Modifier.padding(8.dp).size(30.dp),
                        tint = Color.Red
                    )
                },
                actions = {
                    Surface(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            "DashBoard",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(selectedTab, onTabSelected = { selectedTab = it }, navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Light gray background
                .padding(16.dp)
        ) {
            Text("Quick actions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions Grid
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                QuickActionCard("AI will Advisor", "Write Will", Modifier.weight(1f)) {
                    navController.navigate("chatbot")
                }
                QuickActionCard("Custodians", "2 assigned", Modifier.weight(1f)) {
                    navController.navigate("custodian_management")
                }
                
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                QuickActionCard("Assets", "3 added", Modifier.weight(1f)) {
                    navController.navigate("assets")
                }
                QuickActionCard("Life Goals", "PhD, MBBS", Modifier.weight(1f)) {
                    navController.navigate("life_goals")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Custodian Status", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // Custodian Status List
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { CustodianStatusItem("Ahmed", "Brother", "Accept") }
                item { CustodianStatusItem("Ali", "Son", "Pending") }
            }
        }
    }
}

@Composable
fun QuickActionCard(title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun CustodianStatusItem(name: String, relation: String, status: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Text(relation, fontSize = 12.sp, color = Color.Gray)
            }
            Text(status, fontWeight = FontWeight.Bold, color = if (status == "Accept") Color.Black else Color.Gray)
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit, navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        val items = listOf<ImageVector>(
            Icons.Default.Home,
            Icons.Default.Description,
            Icons.Default.Face,
            Icons.Default.AccountCircle
        )
        val routes = listOf("home", "will", "chatbot", "profile")

        items.forEachIndexed { index, icon ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = { 
                    onTabSelected(index)
                    if (navController.currentBackStackEntry?.destination?.route != routes[index]) {
                        navController.navigate(routes[index])
                    }
                },
                icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(30.dp)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
