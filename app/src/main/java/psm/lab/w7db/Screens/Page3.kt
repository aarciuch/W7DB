package psm.lab.w7db.Screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import psm.lab.w7db.DB.DbVm
import java.io.File

@Composable
fun Page3(navController: NavController, viewModel : DbVm = koinViewModel()) {
    var lista = viewModel.personList.collectAsState().value

    val context = LocalContext.current

    val photoUri1 by viewModel.photoUri1.collectAsState()
    val photoBitmap by viewModel.photoBitmap.collectAsState()


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
            val saveToFileLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.CreateDocument("text/plain")
            ) { uri : Uri? ->
                if (uri != null) {
                    viewModel.saveToFilePublic(uri)
                }
            }
            Button(onClick = { saveToFileLauncher.launch(nazwaPliku) },
                //modifier = Modifier.weight(1f)
            ) {
                Text("Zapis 2")
            }
            val readFromFileLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument()
            ) { uri : Uri? ->
                if (uri != null) {
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    viewModel.readFromFilePublic(uri)
                }
            }
            Button(onClick = { readFromFileLauncher.launch(arrayOf("text/plain")) },
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
        Text("Zdjęcia",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=16.dp),
            textAlign = TextAlign.Center)
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column() {
                val imageUri: Uri = remember {
                    val file = File(context.cacheDir, "photo.jpg")
                    FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
                }
                val takePictureLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicture()
                ) { success ->
                    if (success) {
                        viewModel.onPhotoCaptured(imageUri)
                    }
                }
                Button(
                    onClick = { takePictureLauncher.launch(imageUri) },
                    //modifier = Modifier.weight(1f)
                ) {
                    Text("Zdjęcie 1")
                }
                photoUri1?.let { uri ->
                    val bitmap = remember(uri) {
                        uriToBitmap(context, uri)
                    }
                    bitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Zdjęcie z aparatu",
                            modifier = Modifier.size(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Column() {
                val takePictureLauncher2 = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.TakePicturePreview()
                ) { bitmap : Bitmap? ->
                    if (bitmap != null) {
                        viewModel.onPhotoCaptured2(bitmap)
                    }
                }
                Button(
                    onClick = { takePictureLauncher2.launch(null)},
                    //modifier = Modifier.weight(1f)
                ) {
                    Text("Zdjęcie 2")
                }

                photoBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Zdjęcie z aparatu",
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        val createImageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument("image/jpeg")
        ) { uri: Uri? ->
            if (uri != null) {
                viewModel.saveImage(uri)
            }
        }
        if (photoBitmap != null) {
            Button(
                onClick = { createImageLauncher.launch("photo.jpg") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Zapis zdjęcia do pliku")
            }
            Button(
                onClick = { viewModel.clearPhotoBitmap() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Usunięcie obrazka 2")
            }
        }

        val openDocumentLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            uri?.let {
                // Nadaj trwałe uprawnienia (jeśli chcesz używać później)
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.loadImage(it)
            }
        }
        Button(
            onClick = { openDocumentLauncher.launch(arrayOf("image/jpeg")) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Wczytanie obrazka")
        }
        Spacer(modifier = Modifier.height(16.dp))
        photoBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Wybrane zdjęcie",
                modifier = Modifier.size(250.dp),
                contentScale = ContentScale.Crop
            )
        } ?: Text("Nie ma zdjęcia")
    }
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}


