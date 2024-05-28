package com.uit.moneykeeper.home.viewmodel

import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.models.ViModel

class HomeScreenViewModel {
    fun AddNewWallet(Action: String, walletList: List<ViModel>, tenVi: String, soDu: Double) {
        if(Action == "Cancel") return;
        val newWallet = ViModel(id = walletList.size+1, ten = tenVi, soDu = soDu)
        val updateWallet = walletList + newWallet
        println("new wallets: " + updateWallet)
        GlobalObject.updateListVi(updateWallet)
    }

}