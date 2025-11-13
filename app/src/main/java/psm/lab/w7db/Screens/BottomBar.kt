package psm.lab.w7db.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(Screens.Page1.name)},
            icon = { Icon(Icons.Filled.Person, Screens.Page1.name) },
            label = {Text("Lista")}
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(Screens.Page2.name)},
            icon = { Icon(Icons.Default.Add, Screens.Page2.name) },
            label = {Text("Dodawanie")}
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(Screens.Page3.name)},
            icon = { Icon(Icons.Default.Lock, Screens.Page3.name) },
            label = {Text("Plik")}
        )
    }
}