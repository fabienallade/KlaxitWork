package com.fabien.klaxitwork.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fabien.klaxitwork.api.SearchApi
import com.fabien.klaxitwork.models.AdressModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun SearchScreen(navController: NavController = rememberNavController()) {
    var textFieldValue = remember { mutableStateOf("") }
    var addresses: MutableState<AdressModel?> = remember {
        mutableStateOf(null)
    }

    Scaffold(modifier = Modifier,
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = ""
                    )
                }
            }, title = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textFieldValue.value,
                    onValueChange = {
                        textFieldValue.value = it
                        search(it, addresses)
                    },
                    colors = TextFieldDefaults
                        .textFieldColors(cursorColor = Color.White)
                )
            })
        }) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                if (addresses.value != null) {
                    if (addresses.value!!.features.isEmpty()) {
                        item {
                            Text(
                                text = "Aucune adresse trouve pour cette recherche",
                                fontSize = 20.sp
                            )
                        }
                    }
                    items(count = addresses.value!!.features.size) { index ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.previousBackStackEntry
                                        ?.savedStateHandle
                                        ?.set(
                                            "adress",
                                            addresses.value?.features?.get(index)?.properties?.label.toString()
                                        )
                                    navController.popBackStack()
                                },
                        ) {
                            Text(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                                text = addresses.value?.features?.get(index)?.properties?.label.toString(),
                                fontSize = 20.sp
                            )
                        }
                    }
                } else {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Aucune adresse pour le moment", fontSize = 20.sp)
                        }
                    }

                }

            })
        }
    }
}

fun search(input: String, adress: MutableState<AdressModel?>) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api-adresse.data.gouv.fr")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(SearchApi::class.java)

    val call: Call<AdressModel>? = api.getSearchResult(input)


    call!!.enqueue(object : Callback<AdressModel?> {
        override fun onResponse(call: Call<AdressModel?>, response: Response<AdressModel?>) {
            if (response.isSuccessful) {
                adress.value = response.body()!!
                Log.e("fabien", response.body()?.features.toString())
            }
        }

        override fun onFailure(call: Call<AdressModel?>, t: Throwable) {
            Log.e("fabien", "ceci est un failure")
            Log.e("fabien", t.message.toString())
            adress.value = null
        }

    })
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SearchScreenPreview() {
    SearchScreen()
}