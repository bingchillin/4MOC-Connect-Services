package com.example.connect_services.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.connect_services.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelectComponent(currentService : String) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(currentService) }

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