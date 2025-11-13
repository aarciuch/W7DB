package psm.lab.w7db.Screens

import android.R.attr.onClick
import android.R.attr.top
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinActivityViewModel
import psm.lab.w7db.DB.DbVm

@Composable
fun Page1(navController: NavController, viewModel: DbVm = koinActivityViewModel()) {
    val count by viewModel.PersonsCount.collectAsState(initial = 0)
    val list by viewModel.persons.collectAsState(emptyList())

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        item {
            Text("Lista osób\nLiczba osób: $count")
        }
        items(list) { elem ->
            var newName by remember { mutableStateOf(elem.name) }
            var isEditing by remember { mutableStateOf(false) }

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
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Row (verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(Color.LightGray)
                                .padding( start = 8.dp, end = 8.dp)
                        ) {
                            Checkbox(
                                checked = isEditing,
                                onCheckedChange = { isEditing = it },
                            )
                            Text("Modyfikacja")
                        }
                        Button(
                            onClick = { viewModel.deletePerson(elem) },
                            modifier = Modifier
                                .padding(top = 8.dp)
                        ) {
                            Text("Usunięcie")
                        }
                    }
                    if (isEditing) {
                        TextField(
                            value = newName,
                            onValueChange = { newName = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = {Text("wpisanie nazwy")}
                        )
                        Button(
                            onClick = {
                                val updated = elem.copy(name = newName)
                                viewModel.updatePerson(updated)
                                isEditing = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Text("Modyfikacja")
                        }
                    }
                }
            }
        }
    }
}