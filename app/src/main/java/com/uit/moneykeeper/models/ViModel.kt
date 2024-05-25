package com.uit.moneykeeper.models
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ViModel(
    val id: Int,
    val ten: String,
    val soDu: Double,
) {
    constructor() : this(0, "", 0.0)
}