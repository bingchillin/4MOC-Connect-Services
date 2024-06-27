package com.example.connect_services.components

import android.content.Intent
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.connect_services.CreateAccountActivity
import com.example.connect_services.R

@Composable
fun FAButton() {
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
            )
        },
    )
}