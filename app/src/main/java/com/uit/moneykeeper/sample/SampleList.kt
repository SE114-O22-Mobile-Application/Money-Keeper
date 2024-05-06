package com.uit.moneykeeper.sample

import androidx.compose.ui.graphics.Color
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.transaction.components.IconEnum
import java.time.LocalDate

val loaiGiaoDichList = listOf(
    LoaiGiaoDichModel(Color.Green, "An uong", PhanLoai.Chi, IconEnum.AnUong),
    LoaiGiaoDichModel(Color.Red, "Du lich", PhanLoai.Chi, IconEnum.DuLich),
    LoaiGiaoDichModel(Color.Blue, "Luong", PhanLoai.Thu, IconEnum.Luong)
)

// List of TaiKhoanModel objects
val taiKhoanList = listOf(
    ViModel(1000.0, "Tien mat"),
    ViModel(500.0, "Ngan hang")
)

// List of GiaoDichModel objects
val giaoDichList = listOf(
    GiaoDichModel("An uong", 15000000.0, LocalDate.of(2024, 5, 1), "", loaiGiaoDichList[0], taiKhoanList[0], 1),
    GiaoDichModel("Luong", 500000.0, LocalDate.of(2024, 5, 1), "Nap tien", loaiGiaoDichList[2], taiKhoanList[0], 2),
    GiaoDichModel("Du lich", 200000.0, LocalDate.of(2024, 5, 2), "", loaiGiaoDichList[1], taiKhoanList[1], 3),
    GiaoDichModel("Du lich", 75000.5028, LocalDate.of(2024, 5, 2), "Rut tien", loaiGiaoDichList[1], taiKhoanList[0], 4),
    GiaoDichModel("Luong", 150000.0, LocalDate.of(2024, 5, 3), "", loaiGiaoDichList[2], taiKhoanList[1], 5),
    GiaoDichModel("An uong", 100000.0, LocalDate.of(2024, 5, 3), "", loaiGiaoDichList[0], taiKhoanList[0], 6),
    GiaoDichModel("Luong", 500000.0, LocalDate.of(2024, 5, 4), "Nap tien", loaiGiaoDichList[2], taiKhoanList[0], 7),
    GiaoDichModel("Du lich", 200000.0, LocalDate.of(2024, 5, 4), "", loaiGiaoDichList[1], taiKhoanList[1], 8),
    GiaoDichModel("Du lich", 7500000.0, LocalDate.of(2024, 5, 5), "Rut tien", loaiGiaoDichList[1], taiKhoanList[0], 9),
    GiaoDichModel("Luong", 150000.0, LocalDate.of(2024, 5, 5), "Chuyen khoan", loaiGiaoDichList[2], taiKhoanList[1], 10)
)