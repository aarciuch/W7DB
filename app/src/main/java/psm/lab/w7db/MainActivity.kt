package psm.lab.w7db

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinActivityViewModel
import psm.lab.w7db.DB.DbVm
import psm.lab.w7db.DB.PersonRepository
import psm.lab.w7db.Screens.AppNavHost
import psm.lab.w7db.ui.theme.W7DBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //enableEdgeToEdge()
        setContent {
            W7DBTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues())
                ) {
                   AppNavHost()
                }
            }
        }
    }
}





