package psm.lab.w7db.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import org.koin.compose.viewmodel.koinViewModel
import psm.lab.w7db.DB.DbVm

@Composable
fun Page3(navController: NavController, viewModel : DbVm = koinViewModel()) {
    var lista = viewModel.personList.collectAsState().value

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        Text("Operacje na plikach",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        var nazwaPliku by remember { mutableStateOf("lista.txt") }
        TextField(
            value = nazwaPliku,
            onValueChange = {nazwaPliku = it},
            label = {Text("nazwa pliku:")},
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Button(onClick = {viewModel.saveToFilePrivateArea(nazwaPliku)},
                //modifier = Modifier.weight(1f)
            ) {
                Text("Zapis 1")
            }
            Button(onClick = { viewModel.readFromFilePrivateArea(nazwaPliku) },
                //modifier = Modifier.weight(1f)
            ) {
                Text("Odczyt 1")
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Button(onClick = {},
                //modifier = Modifier.weight(1f)
            ) {
                Text("Zapis 2")
            }
            Button(onClick = {},
                //modifier = Modifier.weight(1f)
            ) {
                Text("Odczyt 2")
            }
        }
        LazyColumn() {
            items(lista.size) {index ->
                Log.i("DB3", "${lista}")
                Text(text = lista[index])
            }
        }
    }
}
