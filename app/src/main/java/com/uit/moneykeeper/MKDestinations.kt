package com.uit.moneykeeper

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
    override val inactivatedIcon = R.drawable.baseline_pie_chart_outline_24
    override val activatedIcon = R.drawable.baseline_pie_chart_24
    override val label = "Thống kê"
    override val route = "account"
}

val mkScreens = listOf(Home, Transaction, Budget, Account)