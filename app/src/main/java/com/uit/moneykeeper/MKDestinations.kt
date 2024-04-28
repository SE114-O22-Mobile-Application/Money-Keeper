package com.uit.moneykeeper

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.res.vectorResource

interface MKDestination {
    val inactivatedIcon: Int
    val activatedIcon: Int
    val route: String
    val label: String
}

object Home : MKDestination {
    override val inactivatedIcon = R.drawable.home_fill0_wght400_grad0_opsz24
    override val activatedIcon = R.drawable.home_fill1_wght400_grad0_opsz24
    override val label = "Trang chủ"
    override val route = "home"
}

object Transaction : MKDestination {
    override val inactivatedIcon = R.drawable.currency_exchange_fill0_wght400_grad0_opsz24
    override val activatedIcon = R.drawable.currency_exchange_fill0_wght400_grad0_opsz24
    override val label = "Giao dịch"
    override val route = "transaction"
}

object Budget : MKDestination {
    override val inactivatedIcon = R.drawable.payments_rounded
    override val activatedIcon = R.drawable.payments_filled
    override val label = "Ngân sách"
    override val route = "budget"
}

object Account : MKDestination {
    override val inactivatedIcon = R.drawable.account_circle_fill0_wght400_grad0_opsz24
    override val activatedIcon = R.drawable.account_circle_fill1_wght400_grad0_opsz24
    override val label = "Tài khoản"
    override val route = "account"
}

val mkScreens = listOf(Home, Transaction, Budget, Account)