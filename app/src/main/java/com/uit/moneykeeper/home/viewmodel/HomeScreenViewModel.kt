package com.uit.moneykeeper.home.viewmodel

import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.global.GlobalObject

class HomeScreenViewModel {
    fun AddNewWallet(Action: String, tenVi: String, soDu: Double, walletList: List<ViModel>) {
        if(Action == "Cancel") return;
        val newWallet = ViModel(soDu = soDu, ten = tenVi, id = walletList.size+1)
        val updateWallet = walletList + newWallet
        println("new wallets: " + updateWallet)
        GlobalObject.updateListVi(updateWallet)
    }
}