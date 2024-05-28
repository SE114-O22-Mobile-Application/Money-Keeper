package com.uit.moneykeeper.global

import com.uit.moneykeeper.models.CTNganSachModel
import com.uit.moneykeeper.models.NganSachModel
import androidx.compose.ui.graphics.Color
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.transaction.components.IconEnum
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
        updateListVi(viList)
        updateListLoaiGiaoDich(loaiGiaoDichList)
        updateListNganSach(nganSachList)
        updateListCTNganSach(ctNganSachList)

        //updateListGiaoDich
        val db = Firebase.firestore
        db.collection("giaoDich").get().addOnSuccessListener { result ->
            val list = result.mapNotNull { document ->
                document.get("loaiGiaoDich")?.let { map ->
                    (map as? Map<*, *>)?.let { loaiGiaoDichMap ->
                        LoaiGiaoDichModel(
                            loaiGiaoDichMap["id"] as? Int ?: 0,
                            loaiGiaoDichMap["ten"] as? String ?: "",
                            PhanLoai.valueOf(loaiGiaoDichMap["loai"] as? String ?: ""),
                            loaiGiaoDichMap["mauSac"]?.let { colorString ->
                                (colorString as? String)?.let { colorStr ->
                                    val colorValues = colorStr.removeSurrounding("Color(", ")").split(", ").mapNotNull {
                                        it.split("=").takeIf { split -> split.size > 1 }?.get(1)?.toFloat()
                                    }
                                    if (colorValues.size >= 4) {
                                        Color(
                                            colorValues[0],
                                            colorValues[1],
                                            colorValues[2],
                                            colorValues[3]
                                        )
                                    } else {
                                            Color.Black
                                        }
                                    }
                            } ?: Color.Black,
                            loaiGiaoDichMap["icon"]?.let { iconString ->
                                (iconString as? String)?.let { IconEnum.valueOf(it) }
                            } ?: IconEnum.Null // Replace 'Default' with your default enum value
                        )
                    }
                }?.let {
                    GiaoDichModel(
                        (document.get("id") as? Long)?.toInt() ?: 0,
                        GlobalFunction.convertTimestampToLocalDate(
                            document.getTimestamp("ngayGiaoDich") ?: Timestamp.now()
                        ),
                        document.getDouble("soTien") ?: 0.0,
                        document.getString("ten") ?: "",
                        // Convert Map to LoaiGiaoDichModel
                        it,
                        // Convert Map to ViModel
                        document.get("vi")?.let { map ->
                            (map as? Map<*, *>)?.let { viMap ->
                                ViModel(
                                    viMap["id"] as? Int ?: 0,
                                    viMap["ten"] as? String ?: "",
                                    viMap["soDu"] as? Double ?: 0.0
                                )
                            } ?: ViModel(0, "", 0.0) // Default ViModel
                        } ?: ViModel(0, "", 0.0), // Default ViModel
                        document.getString("ghiChu") ?: ""
                    )
                }
            }
            _listGiaoDich.value = list
        }
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