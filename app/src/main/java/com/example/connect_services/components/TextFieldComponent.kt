package com.example.connect_services.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TextFieldComponent(
    value: String,
    label: Int,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        label = {
            Text(text = stringResource(id = label))
        },
        onValueChange = {
            onValueChange(it)
        },
        modifier = Modifier.fillMaxWidth()
    )
}