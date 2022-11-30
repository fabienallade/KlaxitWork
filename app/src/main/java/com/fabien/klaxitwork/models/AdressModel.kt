package com.fabien.klaxitwork.models


data class AdressModel(
    val type: String,
    val features: List<AddressFeaturesModel>
)
data class AddressFeaturesModel(val properties:AdressDataModel)
data class AdressDataModel(val label:String)