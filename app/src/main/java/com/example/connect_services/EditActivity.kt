package com.example.connect_services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.activity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "editAccount") {
                composable("editAccount") {
                    EditPage(auid, idService, password, service, onNavigateToMain = { navController.navigate(route = "main") })
                }
                activity("main") {
                    this.activityClass = MainActivity::class
                }
            }

        }
    }
}

@Composable
fun EditPage(auid: Long, idService: String, password: String, service: String, onNavigateToMain: ()-> Unit) {
    val context = LocalContext.current
    var isDarkTheme by remember { mutableStateOf(isDarkTheme(context)) }

    ConnectServicesTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                TopBar(
                    id = R.string.edit_page,
                    onToggleTheme = {
                        isDarkTheme = !isDarkTheme
                        saveTheme(context, isDarkTheme)
                    },
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
                modifier = Modifier.padding(innerPadding),
                onNavigateToMain = onNavigateToMain
            )
        }
    }
}

@Composable
fun EditContent(auid: Long, idService: String, password: String, service: String, modifier: Modifier = Modifier, onNavigateToMain: () -> Unit) {
    val context = LocalContext.current

    var idServiceState by remember { mutableStateOf(idService) }
    var passwordState by remember { mutableStateOf(password) }
    var serviceState by remember { mutableStateOf(service) }
    var isSaved by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) } // État pour gérer la visibilité de l'AlertDialog

    ConnectServicesTheme {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            if (isSaved) {
                Text(
                    text = stringResource(R.string.changes_saved),
                    color = Color.Green,
                    modifier = Modifier.padding(8.dp)
                )
            }

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

                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.button_delete),
                        tint = Color.Red
                    )
                }// Affiche l'AlertDialog
                Spacer(modifier = Modifier.padding(8.dp))

                IconButton(
                    onClick = { onNavigateToMain() },
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.button_cancel),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))

                IconButton(
                    onClick = { _onSaveClick(context, auid, idServiceState, passwordState, serviceState)  { isSaved = true } },
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = stringResource(R.string.button_save),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Affichage de l'AlertDialog
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        _onDeleteClick(context, auid)
                        showDialog = false
                        onNavigateToMain()
                    }) {
                        Text(text = stringResource(R.string.confirm))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                },
                title = {
                    Text(text = stringResource(R.string.confirm_delete_title))
                },
                text = {
                    Text(text = stringResource(R.string.confirm_delete_message))
                }
            )
        }
    }
}

fun _onSaveClick(context: Context, auid: Long, idService: String, password: String, service: String, onSaved: () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "MyAccount.db").build()
        val accountUserDao = db.accountUserDao()
        val serviceAPI = ServiceAPI()

        serviceAPI.updateAccountUserService(accountUserDao, auid, idService, password, service)

        // Vérification après la mise à jour
        val updatedUser = accountUserDao.getUserByAuid(auid)
        if (updatedUser != null &&
            updatedUser.idService == idService &&
            updatedUser.password == password &&
            updatedUser.service == service) {
            onSaved()
        }
    }
}

fun _onDeleteClick(context: Context, auid: Long) {
    CoroutineScope(Dispatchers.IO).launch {
        val db = Room.databaseBuilder(context, AppDatabase::class.java, "MyAccount.db").build()
        val accountUserDao = db.accountUserDao()
        val serviceAPI = ServiceAPI()

        serviceAPI.deleteAccountUserService(accountUserDao, auid)

        // Vérification après la mise à jour
        val deletedUser = serviceAPI.getAccountUserByAuid(accountUserDao,auid)

        deletedUser ?: println("Utilisateur bien supprimé !")
    }
}