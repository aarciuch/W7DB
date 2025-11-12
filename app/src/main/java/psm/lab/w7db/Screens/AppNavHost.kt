package psm.lab.w7db.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {BottomBar(navController = navController)},
        content = { papddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screens.Page1.name,
                modifier = Modifier.padding(paddingValues = papddingValues)
            ) {
                composable(route = Screens.Page1.name) {
                    Page1(navController)
                }
                composable(route = Screens.Page2.name) {
                    Page2(navController)
                }
            }
        }
    )
}