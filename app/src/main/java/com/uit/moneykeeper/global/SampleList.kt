package com.uit.moneykeeper.global

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.models.CTNganSachModel
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.NganSachModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.transaction.components.IconEnum
import java.time.LocalDate

val loaiGiaoDichList = listOf(
    LoaiGiaoDichModel(1, "Ăn uống", PhanLoai.Chi, Color(0xFF008000), IconEnum.AnUong),
    LoaiGiaoDichModel(2, "Du lịch", PhanLoai.Chi, Color(0xFFFF0000), IconEnum.DuLich),
    LoaiGiaoDichModel(3, "Lương", PhanLoai.Thu, Color(0xFF0000FF), IconEnum.Luong)
)

val viList = listOf(
    ViModel(1, "Tiền mặt", 1000000.0),
    ViModel(2, "Chuyển khoản", 2000000.0),
    ViModel(3, "Vietcombank", 3000000.0),
    ViModel(4, "SCB", 4000000.0),
    ViModel(5, "BIDV", 5000000.0),
)

val giaoDichList = listOf(
    GiaoDichModel(1, LocalDate.of(2024, 5, 1), 15000000.0, "Ăn uống", loaiGiaoDichList[0], viList[0], ""),
    GiaoDichModel(2, LocalDate.of(2024, 5, 1), 500000.0, "Lương", loaiGiaoDichList[2], viList[0], "Nạp tiền"),
    GiaoDichModel(3, LocalDate.of(2024, 5, 2), 200000.0, "Du lịch", loaiGiaoDichList[1], viList[1], ""),
    GiaoDichModel(4, LocalDate.of(2024, 5, 2), 75000.0, "Du lịch", loaiGiaoDichList[1], viList[0], "Rút tiền"),
    GiaoDichModel(5, LocalDate.of(2024, 5, 3), 150000.0, "Lương", loaiGiaoDichList[2], viList[1], ""),
    GiaoDichModel(6, LocalDate.of(2024, 5, 3), 100000.0, "Ăn uống", loaiGiaoDichList[0], viList[0], ""),
    GiaoDichModel(7, LocalDate.of(2024, 5, 4), 500000.0, "Lương", loaiGiaoDichList[2], viList[0], "Nạp tiền"),
    GiaoDichModel(8, LocalDate.of(2024, 5, 4), 200000.0, "Du lịch", loaiGiaoDichList[1], viList[1], ""),
    GiaoDichModel(9, LocalDate.of(2024, 5, 5), 7500000.0, "Du lịch", loaiGiaoDichList[1], viList[0], "Rút tiền"),
    GiaoDichModel(10, LocalDate.of(2024, 4, 1), 150000.0, "Lương", loaiGiaoDichList[2], viList[1], "Chuyển khoản"),
    GiaoDichModel(11, LocalDate.of(2024, 4, 2), 1500000.0, "Ăn uống", loaiGiaoDichList[0], viList[0], ""),
    GiaoDichModel(12, LocalDate.of(2024, 4, 3), 1500000.0, "Lương", loaiGiaoDichList[2], viList[0], "Nạp tiền"),
    GiaoDichModel(13, LocalDate.of(2024, 4, 3), 200000.0, "Du lịch", loaiGiaoDichList[1], viList[1], ""),
    GiaoDichModel(14, LocalDate.of(2024, 4, 4), 750000.0, "Du lịch", loaiGiaoDichList[1], viList[0], "Rút tiền"),
    GiaoDichModel(15, LocalDate.of(2024, 4, 4), 1500000.0, "Lương", loaiGiaoDichList[2], viList[1], ""),
    GiaoDichModel(16, LocalDate.of(2024, 6, 5), 150000.0, "Ăn uống", loaiGiaoDichList[0], viList[0], ""),
    GiaoDichModel(17, LocalDate.of(2024, 6, 6), 500000.0, "Lương", loaiGiaoDichList[2], viList[0], ""),
    GiaoDichModel(18, LocalDate.of(2024, 6, 6), 250000.0, "Du lịch", loaiGiaoDichList[1], viList[1], ""),
    GiaoDichModel(19, LocalDate.of(2024, 6, 7), 750000.0, "Du lịch", loaiGiaoDichList[1], viList[0], "Rút tiền"),
    GiaoDichModel(20, LocalDate.of(2024, 6, 8), 150000.0, "Lương", loaiGiaoDichList[2], viList[1], "Chuyển khoản")
)

val nganSachList = listOf(
    NganSachModel(2, LocalDate.now().minusMonths(2), 5000000.0),
    NganSachModel(1, LocalDate.now().minusMonths(1), 5000000.0),
)

val ctNganSachList = listOf(
    CTNganSachModel(1, nganSachList[0], loaiGiaoDichList[0].ten, loaiGiaoDichList[0],3000000.0),
    CTNganSachModel(2, nganSachList[0], loaiGiaoDichList[1].ten, loaiGiaoDichList[1],2000000.0),
    CTNganSachModel(3, nganSachList[1], loaiGiaoDichList[0].ten, loaiGiaoDichList[0],1000000.0),
    CTNganSachModel(4, nganSachList[1], loaiGiaoDichList[1].ten, loaiGiaoDichList[1],4000000.0),
)

//fun uploadgiaoDichSamples() {
//    val db = Firebase.firestore
//
//    giaoDichList.forEach { giaoDich ->
//        val giaoDichMap = mapOf(
//            "id" to giaoDich.id,
//            "ngayGiaoDich" to GlobalFunction.convertLocalDateToTimestamp(giaoDich.ngayGiaoDich),
//            "ten" to giaoDich.ten,
//            "soTien" to giaoDich.soTien,
//            "loaiGiaoDich" to mapOf(
//                "id" to giaoDich.loaiGiaoDich.id,
//                "ten" to giaoDich.loaiGiaoDich.ten,
//                "loai" to giaoDich.loaiGiaoDich.loai.name,
//                "mauSac" to giaoDich.loaiGiaoDich.mauSac.toString(),
//                "icon" to giaoDich.loaiGiaoDich.icon.name
//            ),
//            "vi" to mapOf(
//                "id" to giaoDich.vi.id,
//                "ten" to giaoDich.vi.ten,
//                "soDu" to giaoDich.vi.soDu
//            ),
//            "ghiChu" to giaoDich.ghiChu
//        )
//
//        db.collection("giaoDich")
//            .add(giaoDichMap)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                println("Error adding document: $e")
//            }
//    }
//}

//fun uploadLoaiGiaoDichSamples(){
//    val db = Firebase.firestore
//
//    loaiGiaoDichList.forEach { loaiGiaoDich ->
//        val loaiGiaoDichMap = mapOf(
//            "id" to loaiGiaoDich.id,
//            "ten" to loaiGiaoDich.ten,
//            "loai" to loaiGiaoDich.loai.toString(),
//            "mauSac" to loaiGiaoDich.mauSac.toString(),
//            "icon" to loaiGiaoDich.icon,
//
//        )
//
//        db.collection("loaiGiaoDich")
//            .add(loaiGiaoDichMap)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                println("Error adding document: $e")
//            }
//    }
//}
//
//fun uploadViSamples(){
//    val db = Firebase.firestore
//
//    viList.forEach { vi ->
//        val viMap = mapOf(
//            "id" to vi.id,
//            "ten" to vi.ten,
//            "soDu" to vi.soDu
//        )
//
//        db.collection("vi")
//            .add(viMap)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot added with ID: ${documentReference.id}")
//            }
//            .addOnFailureListener { e ->
//                println("Error adding document: $e")
//            }
//    }
//}