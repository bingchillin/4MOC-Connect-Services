package com.example.connect_services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.connect_services.account.AppDatabase
import com.example.connect_services.components.FAButton
import com.example.connect_services.components.TopBar
import com.example.connect_services.services.ServiceAPI
import com.example.connect_services.ui.theme.ConnectServicesTheme
import com.example.connect_services.view_models.MyCriteriaViewModel
import com.example.connect_services.view_models.MyFormViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private var needRefresh = false

    private val viewModel: MyFormViewModel by viewModels()
    private val criteriaViewModel: MyCriteriaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            var isDarkTheme by remember { mutableStateOf(isDarkTheme(context)) }

            val navController = rememberNavController()
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ConnectServicesTheme(darkTheme = isDarkTheme) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            MyApp(navController)
                        }
                        composable("createAccount") {
                            CreateAccountActivityScreen(
                                innerPadding = innerPadding,
                                viewModel = viewModel,
                                criteriaViewModel = criteriaViewModel,
                                navController = navController
                            )
                        }
                    }
                }

            }

        }
    }

    override fun onResume() {
        super.onResume()
        if (needRefresh) {
            setContent {
                val navController = rememberNavController()
                MyApp(navController)
            }
            needRefresh = false
        }
    }

    override fun onPause() {
        super.onPause()
        needRefresh = true
    }

    fun isDarkTheme(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("dark_theme", false)
    }

    @Composable
    fun MyApp(navController: NavController) {
        val context = LocalContext.current ?: return

        var isDarkTheme by remember { mutableStateOf(isDarkTheme(context)) }

        ConnectServicesTheme(darkTheme = isDarkTheme) {
            Scaffold(
                topBar = { TopBar(id = R.string.account_list, onToggleTheme = {
                    isDarkTheme = !isDarkTheme
                    saveTheme(this@MainActivity, isDarkTheme)
                }, showBackButton = false) },
                floatingActionButton = {
                    FAButton(navController)
                }
            ) { innerPadding ->
                Greeting(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

data class ListItem(val auid: Long, val service: String, val idService: String, val password: String, val icon: ImageVector)

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(context, AppDatabase::class.java, "MyAccount.db").build()
    val accountUserDao = db.accountUserDao()
    val serviceAPI = ServiceAPI()

    var listItems by remember { mutableStateOf(emptyList<ListItem>()) }

    LaunchedEffect(key1 = accountUserDao) {
        val listAccountUser = withContext(Dispatchers.IO) {
            serviceAPI.getAccountUser(accountUserDao)
        }

        listItems = listAccountUser?.map { user ->
            ListItem(
                auid = user.auid,
                service = user.service,
                idService = user.idService,
                password = user.password,
                icon = Icons.Filled.Person
            )
        } ?: emptyList()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(listItems) { item ->
            ListItemComponent(item = item, onItemClick = { listItem ->
                val intent = Intent(context, EditActivity::class.java)
                intent.putExtra("auid", listItem.auid)
                intent.putExtra("service", listItem.service)
                intent.putExtra("idService", listItem.idService)
                intent.putExtra("password", listItem.password)
                context.startActivity(intent)
            })
        }
    }
}

@Composable
fun ListItemComponent(item: ListItem, onItemClick: (ListItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(item) }
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
        Column {
            Text(
                text = item.idService,
            )
            Text(
                text = item.service,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = ContentAlpha.medium),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ConnectServicesTheme {
        //StartActivityScreen()
    }
}

fun isDarkTheme(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isDarkTheme", false)
}

fun saveTheme(context: Context, isDarkTheme: Boolean) {
    val sharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("isDarkTheme", isDarkTheme)
    editor.apply()
}