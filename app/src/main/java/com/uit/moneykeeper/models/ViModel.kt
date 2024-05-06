package com.uit.moneykeeper.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ViModel(
    val soDu: Double,
    val ten: String
) {
    var id: String by mutableStateOf("")

    constructor() : this(0.0, "")
}