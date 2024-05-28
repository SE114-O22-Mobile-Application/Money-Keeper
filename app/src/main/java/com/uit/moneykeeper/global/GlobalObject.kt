package com.uit.moneykeeper.global

import com.uit.moneykeeper.models.CTNganSachModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.NganSachModel
import com.uit.moneykeeper.models.ViModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object GlobalObject {
    private val _listGiaoDich = MutableStateFlow<List<GiaoDichModel>>(emptyList())
    val listGiaoDich: StateFlow<List<GiaoDichModel>> = _listGiaoDich.asStateFlow()

    private val _listVi = MutableStateFlow<List<ViModel>>(emptyList())
    val listVi: StateFlow<List<ViModel>> = _listVi.asStateFlow()

    private val _listLoaiGiaoDich = MutableStateFlow<List<LoaiGiaoDichModel>>(emptyList())
    val listLoaiGiaoDich: StateFlow<List<LoaiGiaoDichModel>> = _listLoaiGiaoDich.asStateFlow()

    private val _listNganSach = MutableStateFlow<List<NganSachModel>>(emptyList())
    val listNganSach: StateFlow<List<NganSachModel>> = _listNganSach.asStateFlow()

    private val _listCTNganSach = MutableStateFlow<List<CTNganSachModel>>(emptyList())
    val listCTNganSach: StateFlow<List<CTNganSachModel>> = _listCTNganSach.asStateFlow()

    init {
        updateListGiaoDich(giaoDichList)
        updateListVi(viList)
        updateListLoaiGiaoDich(loaiGiaoDichList)
        updateListNganSach(nganSachList)
        updateListCTNganSach(ctNganSachList)
    }

    fun updateListGiaoDich(list: List<GiaoDichModel>) {
        _listGiaoDich.value = list
    }

    fun updateListVi(list: List<ViModel>) {
        _listVi.value = list
    }

    fun updateListLoaiGiaoDich(list: List<LoaiGiaoDichModel>) {
        _listLoaiGiaoDich.value = list
    }

    fun updateListNganSach(list: List<NganSachModel>) {
        _listNganSach.value = list
    }

    fun updateListCTNganSach(list: List<CTNganSachModel>) {
        _listCTNganSach.value = list
    }

}