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
    Game,
    TietKiem,
    GiaoDuc,
    SucKhoe,
    QuaTang;

    fun getIcon(): ImageVector {
        return when (this) {
            AnUong -> Icons.Filled.Restaurant
            MuaSam -> Icons.Filled.ShoppingCart
            Luong -> Icons.Filled.Payments
            DuLich -> Icons.Filled.Flight
            Game -> Icons.Filled.SportsEsports
            TietKiem -> Icons.Filled.AttachMoney
            GiaoDuc -> Icons.Filled.School
            SucKhoe -> Icons.Filled.FitnessCenter
            QuaTang -> Icons.Filled.CardGiftcard
            else -> Icons.Filled.Error
        }
    }
}