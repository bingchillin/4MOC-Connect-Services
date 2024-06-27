package com.example.connect_services

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
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
import androidx.compose.material.AlertDialog
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
import androidx.compose.ui.graphics.Color
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
import androidx.room.Room
import com.example.connect_services.account.AppDatabase
import com.example.connect_services.account.user.AccountUser
import com.example.connect_services.services.ServiceAPI
import com.example.connect_services.ui.theme.ConnectServicesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

    var serviceError: Boolean by remember { mutableStateOf(false) }
    var identityError: Boolean by remember { mutableStateOf(false) }
    var passwordError: Boolean by remember { mutableStateOf(false) }

    var serviceIdAlreadyExist: Boolean by remember { mutableStateOf(false) }
    var showDialog: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
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

        Spacer(modifier = Modifier.height(10.dp))

        if (serviceIdAlreadyExist) {
            Text(
                text = "Ce service avec cet identifiant existe déjà.",
                color = Color.Red,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

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
                onValueChange = {
                    serviceValue = it
                    serviceError = serviceValue.isEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(
                        width = if (serviceError) 1.dp else 0.dp,
                        color = if (serviceError) Color.Red else Color.Transparent
                    )


            )

        }
        if (serviceError) {
            Text(
                text = "Service est requis",
                color = Color.Red,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 4.dp)
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
                onValueChange = {
                    identityValue = it
                    identityError = identityValue.isEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(
                        width = if (identityError) 1.dp else 0.dp,
                        color = if (identityError) Color.Red else Color.Transparent
                    )
            )

        }
        if (identityError) {
            Text(
                text = "Identifiant est requis",
                color = Color.Red,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 4.dp)
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
                onValueChange = {
                    passwordValue = it
                    passwordError = passwordValue.isEmpty()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(
                        width = if (passwordError) 1.dp else 0.dp,
                        color = if (passwordError) Color.Red else Color.Transparent
                    )
            )




            Button(onClick = { passwordValue = criteriaViewModel.generatePassword() }) {
                Text(text = "Générer")
            }

        }
        if (passwordError) {
            Text(
                text = "Mot de passe est requis",
                color = Color.Red,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(35.dp))
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
            Button(onClick = {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }) {
                Text(text = "CANCEL")
            }
            Button(onClick = {

                serviceError = serviceValue.isEmpty()
                identityError = identityValue.isEmpty()
                passwordError = passwordValue.isEmpty()

                if (!serviceError && !identityError && !passwordError) {

                    println("1111111111111111111111111111")
                    println("Service: $serviceValue")
                    println("Identifiant: $identityValue")
                    println("Mot de passe: $passwordValue")
                    println("1111111111111111111111111111")

                    CoroutineScope(Dispatchers.IO).launch {

                        serviceIdAlreadyExist = false

                        val db =
                            Room.databaseBuilder(context, AppDatabase::class.java, "MyAccount.db")
                                .build()
                        val accountUserDao = db.accountUserDao()

                        val serviceAPI = ServiceAPI()

                        val checkUserAccountExist =
                            serviceAPI.getAccountUserServiceId(accountUserDao, serviceValue, identityValue)

                        if(!checkUserAccountExist){
                            showDialog = true

                            var accountCreate = AccountUser(
                                service = serviceValue,
                                idService = identityValue,
                                password = passwordValue
                            )

                            serviceAPI.insertAccountUser(accountUserDao, accountCreate)


                            val listAccountUserService =
                                serviceAPI.getAccountUserService(accountUserDao, serviceValue)
                            val listAccountUser = serviceAPI.getAccountUser(accountUserDao)

                            println(listAccountUserService)
                            println("----------------")
                            println(listAccountUser)

                            
                        }else{
                            serviceIdAlreadyExist = true


                            println("Déja present")
                        }







                    }






                }
            }) {
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