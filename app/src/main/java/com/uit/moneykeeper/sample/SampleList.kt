package com.uit.moneykeeper.sample

import androidx.compose.ui.graphics.Color
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
    ViModel(1000.0, "Tien mat", 1),
    ViModel(500.0, "Ngan hang", 2)
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