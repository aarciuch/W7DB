package psm.lab.w7db.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinActivityViewModel
import psm.lab.w7db.DB.DbVm

@Composable
fun Page1(navController: NavController, viewModel : DbVm = koinActivityViewModel() ) {
    val count by viewModel.PersonsCount.collectAsState(initial = 0)
    val list by viewModel.persons.collectAsState(emptyList())
    var modyfikacja by remember { mutableStateOf(false) }


    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)) {
        item {
            Text("Lista osób\nLiczba osób: $count")
        }
        items(list) { elem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.clickable{
                    //    viewModel.deletePerson(elem)
                    //}
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "${elem.id} : ${elem.name}",
                        modifier = Modifier
                            .padding(12.dp),


                        style = MaterialTheme.typography.bodyLarge
                    )
                    Button(
                        onClick = { viewModel.deletePerson(elem) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Usunięcie")
                    }
                    /*Checkbox(
                        checked = modyfikacja, modifier = Modifier.fillMaxWidth(),
                        onCheckedChange = { modyfikacja = it }
                    )
                    if (modyfikacja) {

                        Text("Modyfikacja")
                    }*/
                }
            }
        }
    }
}