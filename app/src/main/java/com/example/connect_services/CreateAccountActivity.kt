package com.example.connect_services

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connect_services.ui.theme.ConnectServicesTheme


class CreateAccountActivity : ComponentActivity() {
    private val viewModel: MyFormViewModel by viewModels()
    private val criteriaViewModel: MyCriteriaViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ConnectServicesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->

                    CreateAccountActivityScreen(innerPadding = innerPadding, viewModel = viewModel,  criteriaViewModel = criteriaViewModel)
                }

            }
        }
    }
}


@Composable
fun CreateAccountActivityScreen(modifier: Modifier = Modifier, innerPadding: PaddingValues, viewModel: MyFormViewModel, criteriaViewModel: MyCriteriaViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "myForm") {
        composable("myForm") {
            MyForm(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                onNavigateToCreate = { navController.navigate(route = "verifCreate") },
                viewModel = viewModel,
                criteriaViewModel = criteriaViewModel
            )
        }
        composable("verifCreate") {
            TestText()
        }
    }
}


@Composable
fun TestText() {
    Text(
        text = "Test",
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 20.dp)
    )
}

@Composable
fun MyForm(
    modifier: Modifier = Modifier,
    viewModel: MyFormViewModel,
    criteriaViewModel: MyCriteriaViewModel,
    onNavigateToCreate: () -> Unit
) {
    val context = LocalContext.current

    var serviceValue: String by rememberSaveable { mutableStateOf(viewModel.serviceValue) }
    var identityValue: String by rememberSaveable { mutableStateOf(viewModel.identityValue) }
    var passwordValue: String by rememberSaveable { mutableStateOf(viewModel.passwordValue) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),  // Ajoutez ceci pour rendre la page scrollable
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "NOUVEAU COMPTE",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Service Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Service:", modifier = Modifier.width(85.dp))
            TextField(
                value = serviceValue,
                onValueChange = { serviceValue = it },
                modifier = Modifier.weight(1f)
            )
        }

        // Identifiant Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Identifiant:", modifier = Modifier.width(85.dp))
            TextField(
                value = identityValue,
                onValueChange = { identityValue = it },
                modifier = Modifier.weight(1f)
            )
        }

        // Mot de passe Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Mot de passe:", modifier = Modifier.width(85.dp))
            TextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { passwordValue = criteriaViewModel.generatePassword() }) {
                Text(text = "Générer")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Critère du mot de passe:")

        Spacer(modifier = Modifier.height(20.dp))
        PasswordDetail(Modifier, criteriaViewModel)

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { onNavigateToCreate() }) {
                Text(text = "CANCEL")
            }
            Button(onClick = { onNavigateToCreate() }) {
                Text(text = "SAVE")
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetail(modifier: Modifier = Modifier, criteriaViewModel: MyCriteriaViewModel) {
    var lengthStringValue by remember { mutableStateOf(criteriaViewModel.lengthStringValue.ifEmpty { "8" }) }

    var isExpanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("majuscule seulement") }

    var isExpandedD by remember { mutableStateOf(false) }
    var selectedDigitTF by remember { mutableStateOf(true) }

    var isExpandedS by remember { mutableStateOf(false) }
    var selectedDigitSC by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        // Length TextField
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Longueur:", modifier = Modifier.width(85.dp))
            TextField(
                value = lengthStringValue,
                onValueChange = {
                    lengthStringValue = it
                    criteriaViewModel.lengthStringValue = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // MAJ/MIN Menu
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it }
            ) {
                TextField(
                    value = selectedItem,
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
                            Text(text = "majuscule seulement")
                        },
                        onClick = {
                            selectedItem = "majuscule seulement"
                            criteriaViewModel.selectedOption = 1
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "minuscule seulement")
                        },
                        onClick = {
                            selectedItem = "minuscule seulement"
                            criteriaViewModel.selectedOption = 2
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "Les deux")
                        },
                        onClick = {
                            selectedItem = "Les deux"
                            criteriaViewModel.selectedOption = 3
                            isExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Digit pwd Menu
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Chiffres:", modifier = Modifier.width(85.dp))
            ExposedDropdownMenuBox(
                expanded = isExpandedD,
                onExpandedChange = { isExpandedD = it }
            ) {
                TextField(
                    value = selectedDigitTF.toString(),
                    onValueChange = { },
                    readOnly = true,
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(expanded = isExpandedD, onDismissRequest = { isExpandedD = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "true")
                        },
                        onClick = {
                            selectedDigitTF = true
                            criteriaViewModel.digitPwd = true
                            isExpandedD = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "false")
                        },
                        onClick = {
                            selectedDigitTF = false
                            criteriaViewModel.digitPwd = false
                            isExpandedD = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Special char Menu
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Caractères spéciaux:", modifier = Modifier.width(150.dp))
            ExposedDropdownMenuBox(
                expanded = isExpandedS,
                onExpandedChange = { isExpandedS = it }
            ) {
                TextField(
                    value = selectedDigitSC.toString(),
                    onValueChange = { },
                    readOnly = true,
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(expanded = isExpandedS, onDismissRequest = { isExpandedS = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "true")
                        },
                        onClick = {
                            selectedDigitSC = true
                            criteriaViewModel.specialChar = true
                            isExpandedS = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = "false")
                        },
                        onClick = {
                            selectedDigitSC = false
                            criteriaViewModel.specialChar = false
                            isExpandedS = false
                        }
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    ConnectServicesTheme {

        //CreateAccountActivityScreen(innerPadding = innerPadding, viewModel = viewModel)
    }
}