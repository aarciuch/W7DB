package psm.lab.w7db.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinActivityViewModel
import psm.lab.w7db.DB.DbVm
import psm.lab.w7db.DB.Person

@Composable
fun Page2(navController: NavController, viewModel : DbVm = koinActivityViewModel()) {
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Dodawanie",
            style = MaterialTheme.typography.titleLarge
        )

        // Pole tekstowe do wpisania opisu osoby
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis osoby") },
            modifier = Modifier.fillMaxWidth()
        )

        // Przycisk dodający rekord do bazy
        Button(
            onClick = {
                // Tworzymy obiekt Person i zapisujemy przez ViewModel
                val person = Person(0,description)
                viewModel.insertPerson(person)
                description = "" // wyczyść pole po dodaniu
                navController.navigate(Screens.Page1.name)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dodaj osobę")
        }
    }
}