package com.example.connect_services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.connect_services.account.AppDatabase
import com.example.connect_services.components.ButtonComponent
import com.example.connect_services.components.TextFieldComponent
import com.example.connect_services.components.TopBar
import com.example.connect_services.services.ServiceAPI
import com.example.connect_services.ui.theme.ConnectServicesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val auid = intent.getLongExtra("auid", 0)
        val service = intent.getStringExtra("service") ?: "Default Service"
        val idService = intent.getStringExtra("idService") ?: "Default idService"
        val password = intent.getStringExtra("password") ?: "Default Password"

        setContent {
            EditPage(auid, idService, password, service)
        }
    }
}

@Composable
fun EditPage(auid: Long, idService: String, password: String, service: String) {
    val isSystemDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(isSystemDarkTheme) }

    Scaffold(
        topBar = {
            TopBar(
                id = R.string.edit_page,
                onToggleTheme = { isDarkTheme = !isDarkTheme },
                showBackButton = true
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        EditContent(
            auid = auid,
            idService = idService,
            password = password,
            service = service,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun EditContent(auid: Long, idService: String, password: String, service: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var idServiceState by remember { mutableStateOf(idService) }
    var passwordState by remember { mutableStateOf(password) }
    var serviceState by remember { mutableStateOf(service) }

    ConnectServicesTheme {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldComponent(
                value = idServiceState,
                label = R.string.user_id,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newValue -> idServiceState = newValue })
            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldComponent(
                value = passwordState,
                label = R.string.user_password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newValue -> passwordState = newValue })
            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldComponent(
                value = serviceState,
                label = R.string.service,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { newValue -> serviceState = newValue })

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ButtonComponent(
                    id = R.string.button_delete,
                    buttonColor = Color.Red,
                    modifier = modifier,
                    onClick = { })
                Spacer(modifier = Modifier.padding(8.dp))
                ButtonComponent(
                    id = R.string.button_cancel,
                    buttonColor = MaterialTheme.colorScheme.secondary,
                    modifier = modifier,
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ButtonComponent(
                    id = R.string.button_save,
                    buttonColor = MaterialTheme.colorScheme.primary,
                    modifier = modifier,
                    onClick = { _onSaveClick(context, auid, idService, password, service) }
                )
            }
        }
    }
}

fun _onSaveClick(context: Context, auid: Long, idService: String, password: String, service: String){
    CoroutineScope(Dispatchers.IO).launch {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "MyAccount.db").build()
        val accountUserDao = db.accountUserDao()
        val serviceAPI = ServiceAPI()

        serviceAPI.updateAccountUserService(accountUserDao, auid, service, idService, password)
    }
}

