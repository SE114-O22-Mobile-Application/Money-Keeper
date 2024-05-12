package com.uit.moneykeeper.transaction.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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