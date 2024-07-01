package com.example.connect_services

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connect_services.components.ButtonComponent
import com.example.connect_services.components.TextFieldComponent
import com.example.connect_services.components.TopBar
import com.example.connect_services.ui.theme.ConnectServicesTheme

class CreateAccountActivity : ComponentActivity() {
    private val viewModel: MyFormViewModel by viewModels()
    private val criteriaViewModel: MyCriteriaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isSystemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(isSystemDarkTheme) }

            ConnectServicesTheme(darkTheme = isDarkTheme) {
                Scaffold(
                    topBar = {
                        TopBar(
                            id = R.string.create_page,
                            onToggleTheme = { isDarkTheme = !isDarkTheme },
                            showBackButton = true
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CreateAccountActivityScreen(
                        innerPadding = innerPadding,
                        viewModel = viewModel,
                        criteriaViewModel = criteriaViewModel
                    )
                }
            }
        }
    }
}



@Composable
fun CreateAccountActivityScreen(innerPadding: PaddingValues, viewModel: MyFormViewModel, criteriaViewModel: MyCriteriaViewModel) {

    val navController = rememberNavController()

    NavHost(navController, startDestination = "myForm") {
        composable("myForm") {
            MyForm(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                viewModel = viewModel,
                criteriaViewModel = criteriaViewModel
            )
        }
    }
}


@Composable
fun MyForm(
    modifier: Modifier = Modifier,
    viewModel: MyFormViewModel,
    criteriaViewModel: MyCriteriaViewModel
) {
    val context = LocalContext.current

    var serviceValue: String by rememberSaveable { mutableStateOf(viewModel.serviceValue) }
    var identityValue: String by rememberSaveable { mutableStateOf(viewModel.identityValue) }
    var passwordValue: String by rememberSaveable { mutableStateOf(viewModel.passwordValue) }

    var serviceError: Boolean by remember { mutableStateOf(false) }
    var identityError: Boolean by remember { mutableStateOf(false) }
    var passwordError: Boolean by remember { mutableStateOf(false) }

    var serviceIdAlreadyExist: Boolean by remember { mutableStateOf(false) }
    var serviceIdIsCreate: Boolean by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            if (serviceIdAlreadyExist) {
                serviceIdIsCreate = false
                Text(
                    text = stringResource(id = R.string.account_already_exist),
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            if (serviceIdIsCreate) {
                serviceIdAlreadyExist = false
                Text(
                    text = stringResource(id = R.string.account_added, serviceValue),
                    color = Color.Green,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Service Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                TextFieldComponent(
                    value = serviceValue,
                    label = R.string.service,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(
                            width = if (serviceError) 1.dp else 0.dp,
                            color = if (serviceError) Color.Red else Color.Transparent
                        ),
                    onValueChange = {
                        serviceValue = it
                        serviceError = serviceValue.isEmpty()
                    })
            }
            if (serviceError) {
                Text(
                    text = stringResource(id = R.string.mandatory_field),
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Identifiant Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextFieldComponent(
                    value = identityValue,
                    label = R.string.id,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .border(
                            width = if (identityError) 1.dp else 0.dp,
                            color = if (identityError) Color.Red else Color.Transparent
                        ),
                    onValueChange = {
                        identityValue = it
                        identityError = identityValue.isEmpty()
                    })
            }
            if (identityError) {
                Text(
                    text = stringResource(id = R.string.mandatory_field),
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Mot de passe Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextFieldComponent(
                        value = passwordValue,
                        label = R.string.password,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .border(
                                width = if (passwordError) 1.dp else 0.dp,
                                color = if (passwordError) Color.Red else Color.Transparent
                            ),
                        onValueChange = {
                            passwordValue = it
                            passwordError = passwordValue.isEmpty()
                        })
                }


            }
            if (passwordError) {
                Text(
                    text = stringResource(id = R.string.mandatory_field),
                    color = Color.Red,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            ButtonComponent(
                id = R.string.button_generate,
                buttonColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(10.dp),
                onClick = { passwordValue = criteriaViewModel.generatePassword() })

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.password_critaria))

            Spacer(modifier = Modifier.height(20.dp))
            PasswordDetail(criteriaViewModel)

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Buttons at the bottom
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ButtonComponent(
                    id = R.string.button_cancel,
                    buttonColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    })

                ButtonComponent(
                    id = R.string.button_save,
                    buttonColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(15.dp),
                    onClick = {
                        CreateAccountActivityFunction().onSaveButtonClick(
                            context = context,
                            viewModel = viewModel,
                            serviceValue = serviceValue,
                            identityValue = identityValue,
                            passwordValue = passwordValue,
                            criteriaViewModel = criteriaViewModel,
                            onSuccess = {
                                serviceIdIsCreate = true
                            },
                            onFailure = {
                                serviceIdAlreadyExist = true
                            }
                        )
                    }
                )

            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetail(criteriaViewModel: MyCriteriaViewModel) {
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
            TextField(
                label = {
                    Text(text = stringResource(id = R.string.text_longueur))
                },
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
                val textMaj = stringResource(R.string.text_only_maj)
                val textMin = stringResource(R.string.text_only_min)
                val textMajMin = stringResource(R.string.text_only_maj_min)

                ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(text = stringResource(id = R.string.text_only_maj))
                        },
                        onClick = {
                            selectedItem = textMaj
                            criteriaViewModel.selectedOption = 1
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text =  stringResource(id = R.string.text_only_min))
                        },
                        onClick = {
                            selectedItem = textMin
                            criteriaViewModel.selectedOption = 2
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text =  stringResource(id = R.string.text_only_maj_min))
                        },
                        onClick = {
                            selectedItem = textMajMin
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
            Text(text = stringResource(id = R.string.text_digit), modifier = Modifier.width(85.dp))
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
                            Text(text =  stringResource(id = R.string.text_true))
                        },
                        onClick = {
                            selectedDigitTF = true
                            criteriaViewModel.digitPwd = true
                            isExpandedD = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text =  stringResource(id = R.string.text_false))
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
            Text(text = stringResource(id = R.string.text_spec_car ), modifier = Modifier.width(150.dp))
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
                            Text(text =  stringResource(id = R.string.text_true))
                        },
                        onClick = {
                            selectedDigitSC = true
                            criteriaViewModel.specialChar = true
                            isExpandedS = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text =  stringResource(id = R.string.text_false))
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