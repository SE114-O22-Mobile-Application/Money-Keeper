package com.uit.moneykeeper.transaction.viewmodel

import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.sample.GlobalObject

class WalletListDropdownViewModel {
    val walletList: List<ViModel> = GlobalObject.listVi.value
}