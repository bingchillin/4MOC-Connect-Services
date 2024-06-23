package com.example.connect_services

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connect_services.ui.theme.ConnectServicesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConnectServicesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = { FAButton() }
                ) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class ListItem(val name: String, val icon: ImageVector)
val itemsList = listOf(
    ListItem(name = "Item 1", icon = Icons.Default.Favorite),
    ListItem(name = "Item 2", icon = Icons.Default.Home),
    ListItem(name = "Item 3", icon = Icons.Default.Settings),
    ListItem(name = "Item 4", icon = Icons.Default.Person),
    ListItem(name = "Item 5", icon = Icons.Default.Phone),
    ListItem(name = "Item 6", icon = Icons.Default.Email),
    ListItem(name = "Item 7", icon = Icons.Default.Info),
    ListItem(name = "Item 8", icon = Icons.Default.Warning),
    ListItem(name = "Item 9", icon = Icons.Default.Search),
    ListItem(name = "Item 10", icon = Icons.Default.Favorite),
    ListItem(name = "Item 11", icon = Icons.Default.Home),
    ListItem(name = "Item 12", icon = Icons.Default.Settings),
    ListItem(name = "Item 13", icon = Icons.Default.Person),
)

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
                    tint = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = item.name,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FAButton() {
    FloatingActionButton(
        onClick = { /*TODO*/ }
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConnectServicesTheme {
        Greeting()
    }
}
