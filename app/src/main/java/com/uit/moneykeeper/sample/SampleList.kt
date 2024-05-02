package com.uit.moneykeeper.sample

import androidx.compose.ui.graphics.Color
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.TaiKhoanModel
import java.time.LocalDate
import java.util.Date

val loaiGiaoDichList = listOf(
    LoaiGiaoDichModel(Color.Green, "An uong", PhanLoai.Chi),
    LoaiGiaoDichModel(Color.Red, "Du lich", PhanLoai.Chi),
    LoaiGiaoDichModel(Color.Blue, "Luong", PhanLoai.Thu)
)

// List of TaiKhoanModel objects
val taiKhoanList = listOf(
    TaiKhoanModel(Color.Red, 1000.0, "Tien mat"),
    TaiKhoanModel(Color. Blue, 500.0, "Ngan hang")
)

// List of GiaoDichModel objects
val giaoDichList = listOf(
    GiaoDichModel(100.0, LocalDate.of(2024, 5, 1), "Mua hang", loaiGiaoDichList[0], taiKhoanList[0], 1),
    GiaoDichModel(50.0, LocalDate.of(2024, 5, 1), "Nap tien", loaiGiaoDichList[2], taiKhoanList[0], 2),
    GiaoDichModel(200.0, LocalDate.of(2024, 5, 2), "Tra tien", loaiGiaoDichList[1], taiKhoanList[1], 3),
    GiaoDichModel(75.0, LocalDate.of(2024, 5, 2), "Rut tien", loaiGiaoDichList[1], taiKhoanList[0], 4),
    GiaoDichModel(150.0, LocalDate.of(2024, 5, 3), "Chuyen khoan", loaiGiaoDichList[2], taiKhoanList[1], 5),
    GiaoDichModel(100.0, LocalDate.of(2024, 5, 3), "Mua hang", loaiGiaoDichList[0], taiKhoanList[0], 6),
    GiaoDichModel(50.0, LocalDate.of(2024, 5, 4), "Nap tien", loaiGiaoDichList[2], taiKhoanList[0], 7),
    GiaoDichModel(200.0, LocalDate.of(2024, 5, 4), "Tra tien", loaiGiaoDichList[1], taiKhoanList[1], 8),
    GiaoDichModel(75.0, LocalDate.of(2024, 5, 5), "Rut tien", loaiGiaoDichList[1], taiKhoanList[0], 9),
    GiaoDichModel(150.0, LocalDate.of(2024, 5, 5), "Chuyen khoan", loaiGiaoDichList[2], taiKhoanList[1], 10)
)