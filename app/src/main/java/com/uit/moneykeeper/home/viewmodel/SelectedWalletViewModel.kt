package com.uit.moneykeeper.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.uit.moneykeeper.models.ViModel
class SelectedWalletViewModel {
    private val _viModel = mutableStateOf(ViModel())

    fun setViModel(viModel: ViModel) {
        _viModel.value = viModel
    }

    fun getViModel(): State<ViModel> {
        return _viModel
    }
}