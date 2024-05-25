package com.uit.moneykeeper.home.viewmodel

import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.global.GlobalObject

class ListWalletViewModel {
    val walletList: List<ViModel> = GlobalObject.listVi.value
}