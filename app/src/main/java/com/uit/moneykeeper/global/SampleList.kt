package com.uit.moneykeeper.global

import androidx.compose.ui.graphics.Color
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.transaction.components.IconEnum
import java.time.LocalDate

val loaiGiaoDichList = listOf(
    LoaiGiaoDichModel(Color.Green, "An uong", PhanLoai.Chi, IconEnum.AnUong, 1),
    LoaiGiaoDichModel(Color.Red, "Du lich", PhanLoai.Chi, IconEnum.DuLich, 2),
    LoaiGiaoDichModel(Color.Blue, "Luong", PhanLoai.Thu, IconEnum.Luong, 3)
)

val viList = listOf(
    ViModel(1000000.0, "Tien mat", 1),
    ViModel(50000.0, "Ngan hang", 2),
    ViModel(9000000.0, "Vietcombank", 3),
    ViModel(200000.9, "SCB", 4),
    ViModel(700000.0, "BIDV", 5),
)

val giaoDichList = listOf(
    GiaoDichModel("An uong", 15000000.0, LocalDate.of(2024, 5, 1), "", loaiGiaoDichList[0], viList[0], 1),
    GiaoDichModel("Luong", 500000.0, LocalDate.of(2024, 5, 1), "Nap tien", loaiGiaoDichList[2], viList[0], 2),
    GiaoDichModel("Du lich", 200000.0, LocalDate.of(2024, 5, 2), "", loaiGiaoDichList[1], viList[1], 3),
    GiaoDichModel("Du lich", 75000.0, LocalDate.of(2024, 5, 2), "Rut tien", loaiGiaoDichList[1], viList[0], 4),
    GiaoDichModel("Luong", 150000.0, LocalDate.of(2024, 5, 3), "", loaiGiaoDichList[2], viList[1], 5),
    GiaoDichModel("An uong", 100000.0, LocalDate.of(2024, 5, 3), "", loaiGiaoDichList[0], viList[0], 6),
    GiaoDichModel("Luong", 500000.0, LocalDate.of(2024, 5, 4), "Nap tien", loaiGiaoDichList[2], viList[0], 7),
    GiaoDichModel("Du lich", 200000.0, LocalDate.of(2024, 5, 4), "", loaiGiaoDichList[1], viList[1], 8),
    GiaoDichModel("Du lich", 7500000.0, LocalDate.of(2024, 5, 5), "Rut tien", loaiGiaoDichList[1], viList[0], 9),
    GiaoDichModel("Luong", 150000.0, LocalDate.of(2024, 4, 1), "Chuyen khoan", loaiGiaoDichList[2], viList[1], 10),
    GiaoDichModel("An uong", 1500000.0, LocalDate.of(2024, 4, 2), "", loaiGiaoDichList[0], viList[0], 11),
    GiaoDichModel("Luong", 1500000.0, LocalDate.of(2024, 4, 3), "Nap tien", loaiGiaoDichList[2], viList[0], 12),
    GiaoDichModel("Du lich", 200000.0, LocalDate.of(2024, 4, 3), "", loaiGiaoDichList[1], viList[1], 13),
    GiaoDichModel("Du lich", 750000.0, LocalDate.of(2024, 4, 4), "Rut tien", loaiGiaoDichList[1], viList[0], 14),
    GiaoDichModel("Luong", 1500000.0, LocalDate.of(2024, 4, 4), "", loaiGiaoDichList[2], viList[1], 15),
    GiaoDichModel("An uong", 150000.0, LocalDate.of(2024, 6, 5), "", loaiGiaoDichList[0], viList[0], 16),
    GiaoDichModel("Luong", 500000.0, LocalDate.of(2024, 6, 6), "Nap tien", loaiGiaoDichList[2], viList[0], 17),
    GiaoDichModel("Du lich", 250000.0, LocalDate.of(2024, 6, 6), "", loaiGiaoDichList[1], viList[1], 18),
    GiaoDichModel("Du lich", 750000.0, LocalDate.of(2024, 6, 7), "Rut tien", loaiGiaoDichList[1], viList[0], 19),
    GiaoDichModel("Luong", 150000.0, LocalDate.of(2024, 6, 8), "Chuyen khoan", loaiGiaoDichList[2], viList[1], 20)
)

fun uploadgiaoDichSamples() {
    val db = Firebase.firestore

    giaoDichList.forEach { giaoDich ->
        val giaoDichMap = mapOf(
            "ten" to giaoDich.ten,
            "soTien" to giaoDich.soTien,
            "ngayGiaoDich" to GlobalFunction.convertLocalDateToTimestamp(giaoDich.ngayGiaoDich),
            "ghiChu" to giaoDich.ghiChu,
            "loaiGiaoDich" to giaoDich.loaiGiaoDich,
            "taiKhoan" to giaoDich.taiKhoan,
            "id" to giaoDich.id
        )

        db.collection("giaoDich")
            .add(giaoDichMap)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
}

fun uploadLoaiGiaoDichSamples(){
    val db = Firebase.firestore

    loaiGiaoDichList.forEach { loaiGiaoDich ->
        val loaiGiaoDichMap = mapOf(
            "mauSac" to loaiGiaoDich.mauSac.toString(),
            "ten" to loaiGiaoDich.ten,
            "loai" to loaiGiaoDich.loai.toString(),
            "icon" to loaiGiaoDich.icon.toString(),
            "id" to loaiGiaoDich.id
        )

        db.collection("loaiGiaoDich")
            .add(loaiGiaoDichMap)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
}

fun uploadViSamples(){
    val db = Firebase.firestore

    viList.forEach { vi ->
        val viMap = mapOf(
            "soDu" to vi.soDu,
            "ten" to vi.ten,
            "id" to vi.id
        )

        db.collection("vi")
            .add(viMap)
            .addOnSuccessListener { documentReference ->
                println("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error adding document: $e")
            }
    }
}
