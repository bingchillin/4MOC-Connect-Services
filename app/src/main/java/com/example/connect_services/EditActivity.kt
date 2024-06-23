package com.example.connect_services

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connect_services.ui.theme.ConnectServicesTheme

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EditPage()
        }
    }
}

@Preview
@Composable
fun EditPage() {
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun EditContent(modifier: Modifier = Modifier) {
    ConnectServicesTheme {
        Column(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            TextFieldComponent(value = "", label = R.string.user_id)
            Spacer(modifier = Modifier.padding(8.dp))
            TextFieldComponent(value = "", label = R.string.user_password)
            Spacer(modifier = Modifier.padding(8.dp))
            DropdownSelectComponent()
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ButtonComponent(id = R.string.button_delete, buttonColor = Color.Red)
                Spacer(modifier = Modifier.padding(8.dp))
                ButtonComponent(
                    id = R.string.button_cancel,
                    buttonColor = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ButtonComponent(
                    id = R.string.button_save,
                    buttonColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun TextFieldComponent(value: String, label: Int /*, onValueChange: (String) -> Unit */) {
    TextField(
        value = value,
        label = {
            Text(text = stringResource(id = label))
        },
        onValueChange = { },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelectComponent() {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        TextField(
            value = selectedItem,
            label = { Text(text = stringResource(id = R.string.user_service)) },
            onValueChange = { },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            DropdownMenuItem(
                text = {
                    Text(text = "Microsoft")
                },
                onClick = {
                    selectedItem = "Microsoft"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Google")
                },
                onClick = {
                    selectedItem = "Google"
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Github")
                },
                onClick = {
                    selectedItem = "Github"
                    isExpanded = false
                }
            )
        }
    }
}

@Composable
fun ButtonComponent(id: Int, buttonColor: Color) {
    Button(
        onClick = { /*TODO*/ },
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
    ) {
        Text(text = stringResource(id = id))
    }
}