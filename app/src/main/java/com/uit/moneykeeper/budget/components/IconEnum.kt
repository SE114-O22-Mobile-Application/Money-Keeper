package com.uit.moneykeeper.budget.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.ui.graphics.vector.ImageVector

enum class IconEnum {
    Null,
    AnUong,
    MuaSam,
    Luong,
    DuLich,
    Game;

    fun getIcon(): ImageVector {
        return when (this) {
            AnUong -> Icons.Filled.Restaurant
            MuaSam -> Icons.Filled.ShoppingCart
            Luong -> Icons.Filled.Payments
            DuLich -> Icons.Filled.Flight
            Game -> Icons.Filled.SportsEsports
            else -> Icons.Filled.Error
        }
    }
}