package com.example.digitalwill

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.digitalwill.ui.theme.DigitalWillTheme
import com.example.digitalwill.view.AIScreen
import com.example.digitalwill.view.AddAssetScreen
import com.example.digitalwill.view.AddCustodianScreen
import com.example.digitalwill.view.AssetsScreen
import com.example.digitalwill.view.BeneficiariesScreen
import com.example.digitalwill.view.CreatePinScreen
import com.example.digitalwill.view.CustodianManagementScreen
import com.example.digitalwill.view.CustodiansScreen
import com.example.digitalwill.view.HomeScreen
import com.example.digitalwill.view.LifeGoalScreen
import com.example.digitalwill.view.Login
import com.example.digitalwill.view.ProfileScreen
import com.example.digitalwill.view.WillScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigitalWillTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DigitalWillNavHost()


                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun DigitalWillNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("login") {
            Login(onNavigate = {
                navController.navigate("custodian")
            })
        }
        composable("custodian") {
            CustodiansScreen(onNavigate = {
                navController.navigate("create_pin")
            })
        }
        composable("create_pin") {
            CreatePinScreen(
                onBack = { navController.popBackStack() } ,
                onContinue = {
                    navController.navigate("home") {
                        popUpTo("create_pin") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("will") {
            WillScreen(
                navController = navController ,
                onBack = {
                    navController.popBackStack()
                })
        }
        composable("chatbot") {
            AIScreen(navController)
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("assets") {
            AssetsScreen(
                onBack = { navController.popBackStack() } ,
                onAddAsset = { navController.navigate("add_asset") })
        }
        composable("life_goals") {
            LifeGoalScreen(navController)
        }
        composable("beneficiaries") {
            BeneficiariesScreen(navController)
        }
        composable("custodian_management") {
            CustodianManagementScreen(navController)
        }
        composable("add_custodian") {
            AddCustodianScreen(navController)
        }
        composable("add_asset") {
            AddAssetScreen(navController)
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000.milliseconds) // 3 seconds delay
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.digitalwill), // Logo image
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {})
}
