package com.uit.moneykeeper.budget.viewmodel

import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.models.CTNganSachModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.NganSachModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import java.time.LocalDate

fun getListNganSach(): List<NganSachModel> {
    return GlobalObject.listNganSach.value;
}

fun getListNganSachByMonthYear(date: LocalDate): List<NganSachModel> {
    return GlobalObject.listNganSach.value.filter { it.thoiGian.year.equals(date.year) && it.thoiGian.monthValue.equals(date.monthValue) };
}

fun getListCTNganSachByNS(ns: NganSachModel): List<CTNganSachModel> {
    return GlobalObject.listCTNganSach.value.filter { it.NganSach.equals(ns) };
}

fun getGiaoDichByMY(date: LocalDate): List<GiaoDichModel> {
    return GlobalObject.listGiaoDich.value.filter {
        it.ngayGiaoDich.monthValue.equals(date.monthValue)
            && it.ngayGiaoDich.year.equals(date.year)}
}

fun getGiaoDichByLGD(list: List<GiaoDichModel>, lgd: LoaiGiaoDichModel): List<GiaoDichModel> {
    return list.filter { it.loaiGiaoDich.equals(lgd) }
}

//fun getAllLGDChi(): List<LoaiGiaoDichModel> {
//    return GlobalObject.listLoaiGiaoDich.value.filter { it.loai == PhanLoai.Chi }
//}

fun getAllLGDChi(callback: FirebaseCallback) {
    val db = Firebase.firestore
    val lgdList = mutableListOf<LoaiGiaoDichModel>()

    db.collection("loaiGiaoDich")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val lgd = document.toObject(LoaiGiaoDichModel::class.java)
                if (lgd.loai == PhanLoai.Chi) {
                    lgdList.add(lgd)
                }
            }
            callback.onCallback(lgdList)
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
}

fun AddNewNganSach(thoiGian: LocalDate, tongTien: Double): NganSachModel {
    val newNganSach = NganSachModel(GlobalObject.listNganSach.value.size + 1, thoiGian = thoiGian,tongTien);
    val updateNganSach = GlobalObject.listNganSach.value + newNganSach
    GlobalObject.updateListNganSach(updateNganSach)
    return newNganSach
}
fun AddNewCTNganSach(ngansach: NganSachModel, lgd: LoaiGiaoDichModel, tien: Double) {
    val newCTNganSach = CTNganSachModel(GlobalObject.listCTNganSach.value.size + 1, ngansach, lgd.ten, lgd, tien);
    val updateCTNganSach = GlobalObject.listCTNganSach.value + newCTNganSach
    GlobalObject.updateListCTNganSach(updateCTNganSach)
}
