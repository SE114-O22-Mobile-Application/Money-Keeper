package com.uit.moneykeeper.home.viewmodel

import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.sample.GlobalObject

class ListWalletViewModel {
    val walletList: List<ViModel> = GlobalObject.listVi.value
}