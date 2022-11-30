package com.fabien.klaxitwork.screens

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeScreen(
    navController: NavController = rememberNavController()
) {
    val address = remember {
        mutableStateOf("")
    }
    val secondScreenResult =
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("adress")
            ?.observeAsState()

    secondScreenResult?.value?.let {
        // Read the result
        Log.e("fabien", it)
        address.value = it
    }
    Scaffold(modifier = Modifier, topBar = {
        TopAppBar(title = {
            Text(
                text = "Mon adresse", fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(.70f)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = address.value, fontSize = 20.sp)
                }
            }
            Column(modifier = Modifier, verticalArrangement = Arrangement.Center) {
                Row(modifier = Modifier) {
                    Button(modifier = Modifier
                        .height(60.dp)
                        .width(120.dp)
                        .padding(bottom = 10.dp),
                        onClick = { navController.navigate("search") }) {
                        Text(
                            text = if (address.value.isEmpty()) "Ajouter" else "Modifier",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun HomeScreenPreview() {
    HomeScreen()
}