package com.uit.moneykeeper.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.uit.moneykeeper.models.ViModel
class DetailWalletViewModel {
    private val _viModelList = mutableStateListOf<ViModel>()

    fun addViModel(viModel: ViModel) {
        _viModelList.add(viModel)
    }

    fun getViModelList(): State<List<ViModel>> {
        return mutableStateOf(_viModelList.toList())
    }
}