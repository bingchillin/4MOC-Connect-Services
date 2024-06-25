package com.example.connect_services

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connect_services.ui.theme.ConnectServicesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val isSystemDarkTheme = isSystemInDarkTheme()
    var isDarkTheme by remember { mutableStateOf(isSystemDarkTheme) }

    ConnectServicesTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = { TopBar(id = R.string.account_list, onToggleTheme = { isDarkTheme = !isDarkTheme }, showBackButton = false) },
            floatingActionButton = {
                FAButton(
                    modifier = Modifier
                        .padding(25.dp)
                        .size(80.dp)
                )
            }
        ) { innerPadding ->
            Greeting(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

data class ListItem(val name: String, val icon: ImageVector)
val itemsList = listOf(
    ListItem(name = "Google", icon = Icons.Default.Favorite),
    ListItem(name = "Snapchat", icon = Icons.Default.Home),
    ListItem(name = "Instagram", icon = Icons.Default.Settings),
    ListItem(name = "Gmail", icon = Icons.Default.Person),
    ListItem(name = "MyGES", icon = Icons.Default.Phone),
    ListItem(name = "CIC", icon = Icons.Default.Email),
    ListItem(name = "CA", icon = Icons.Default.Info),
    ListItem(name = "H&M", icon = Icons.Default.Warning),
    ListItem(name = "ZARA", icon = Icons.Default.Search),
    ListItem(name = "Apple", icon = Icons.Default.Favorite),
    ListItem(name = "Microsoft", icon = Icons.Default.Home),
    ListItem(name = "Celio", icon = Icons.Default.Settings),
    ListItem(name = "Amazon", icon = Icons.Default.Person),
)

@Composable
fun TopBar(id: Int, onToggleTheme: () -> Unit, showBackButton: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (showBackButton) {
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back button"
                )
            }
        }
        Text(
            text = stringResource(id = id),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onToggleTheme) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Theme button"
            )
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(itemsList) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = item.name,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FAButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            val intent = Intent(context, CreateAccountActivity::class.java)
            context.startActivity(intent)
        },
        content = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = stringResource(id = R.string.add),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.scale(1.8f)
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ConnectServicesTheme {
        //StartActivityScreen()
    }
}

