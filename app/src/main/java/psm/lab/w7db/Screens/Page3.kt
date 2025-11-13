package psm.lab.w7db.Screens

import android.util.Log
import android.util.Log.i
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.count
import org.koin.compose.viewmodel.koinActivityViewModel
import psm.lab.w7db.DB.DbVm

@Composable
fun Page3(navController: NavController, viewModel : DbVm = koinActivityViewModel()) {
    var lista = viewModel.personList.collectAsState()
    var i by remember {mutableStateOf(0)}

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        Text("Operacje na plikach",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(horizontalArrangement = Arrangement.Absolute.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            var nazwaPliku by remember { mutableStateOf("lista.txt") }
            TextField(
                value = nazwaPliku,
                onValueChange = {nazwaPliku = it},
                label = {Text("nazwa pliku:")},
                modifier = Modifier
                    .weight(1f)
            )
            Button(onClick = {viewModel.saveToFilePrivate(nazwaPliku)},
                modifier = Modifier.weight(1f)
            ) {
                Text("Zapis")
            }
            Button(onClick = { viewModel.readFromFilePrivate(nazwaPliku) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Odczyt")
            }
        }
        LazyColumn() {
            items(lista.value.size) {index ->
                Log.i("DB3", "${lista.value}")
                Text(text = lista.value[index])
            }
        }
    }
}
