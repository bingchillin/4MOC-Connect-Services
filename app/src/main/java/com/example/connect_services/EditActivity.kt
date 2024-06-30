package com.example.connect_services

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
import com.example.connect_services.components.ButtonComponent
import com.example.connect_services.components.TextFieldComponent
import com.example.connect_services.components.TopBar
import com.example.connect_services.ui.theme.ConnectServicesTheme

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val auid = intent.getLongExtra("auid", 0)
        println("Auid : " + auid)
        val service = intent.getStringExtra("service") ?: "Default Service"
        val idService = intent.getStringExtra("idService") ?: "Default idService"
        val password = intent.getStringExtra("password") ?: "Default Password"

        setContent {
            EditPage(idService, password, service)
        }
    }
}

@Composable
fun EditPage(idService: String, password: String, service: String) {
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
            idService = idService,
            password = password,
            service = service,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun EditContent(idService: String, password: String, service: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    ConnectServicesTheme {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TextFieldComponent(
                value = idService,
                label = R.string.user_id,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { })
            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldComponent(
                value = password,
                label = R.string.user_password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { })
            Spacer(modifier = Modifier.padding(8.dp))

            TextFieldComponent(
                value = service,
                label = R.string.service,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { })

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
                    onClick = { }
                )
            }
        }
    }
}