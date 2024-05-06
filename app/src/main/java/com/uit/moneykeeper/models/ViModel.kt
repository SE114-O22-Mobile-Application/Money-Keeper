package com.uit.moneykeeper.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ViModel(
    val soDu: Double,
    val ten: String,
    val id: Int
) {
    constructor() : this(0.0, "", 0)
}