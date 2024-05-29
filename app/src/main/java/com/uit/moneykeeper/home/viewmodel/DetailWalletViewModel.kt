package com.uit.moneykeeper.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.models.ViModel
class DetailWalletViewModel {
    private val _viModelList = mutableStateListOf<ViModel>()

    fun addViModel(viModel: ViModel) {
        _viModelList.add(viModel)
    }

    fun getViModelList(): State<List<ViModel>> {
        return mutableStateOf(_viModelList.toList())
    }

    fun getViList(): List<ViModel> {
        return GlobalObject.listVi.value;
    }

    fun checkDeleteWallet(wallet: ViModel): Boolean {
        val listGD = GlobalObject.listGiaoDich.value
        println("List GD: " + listGD);
        println("Wallet: " + wallet);
        println("Check: " +  listGD.any { it.vi.id == wallet.id })
        return listGD.any { it.vi.id == wallet.id }
    }
}