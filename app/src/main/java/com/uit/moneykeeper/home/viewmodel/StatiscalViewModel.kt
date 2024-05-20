package com.uit.moneykeeper.home.viewmodel

import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.sample.GlobalObject
import java.time.LocalDate
import androidx.compose.ui.graphics.Color

fun filterData(wallet: ViModel, date1: LocalDate, date2: LocalDate, option: String, typeOfTrans: String): List<GiaoDichModel> {
    val originalList = GlobalObject.listGiaoDich.value
    val filteredByDate = when (option) {
        "Week" -> originalList.filter { trans ->
            (date1.isBefore(trans.ngayGiaoDich) || date1.isEqual(trans.ngayGiaoDich)) &&
                    (date2.isAfter(trans.ngayGiaoDich) || date2.isEqual(trans.ngayGiaoDich))
        }
        "Month" -> originalList.filter { trans ->
            trans.ngayGiaoDich.monthValue == date1.monthValue &&
                    trans.ngayGiaoDich.year == date1.year
        }
        "Year" -> originalList.filter { trans ->
            trans.ngayGiaoDich.year == date1.year
        }
        else -> throw IllegalArgumentException("Invalid option: $option") // Handle invalid options
    }

    val filteredByWallet = if (wallet.id != 0) {
        filteredByDate.filter { trans -> trans.taiKhoan.id == wallet.id } // Filter for transactions in the specified wallet
    } else {
        filteredByDate
    }

    val filteredByTypeTrans = if (typeOfTrans == "Chi") {
        filteredByWallet.filter { trans -> trans.loaiGiaoDich.loai == PhanLoai.Chi } // Filter for transactions in the specified wallet
    } else {
        filteredByWallet.filter { trans -> trans.loaiGiaoDich.loai == PhanLoai.Thu }
    }

    return filteredByTypeTrans
}

fun createColorList(n: Int, type: String): List<Color> {
    if (type == "Thu") {
        // Phân bố đều từ xanh lục sang xanh dương
        val startColor = Color(0xFF007317) // Xanh lục
        val endColor = Color(0x0000FF) // Xanh dương
        val step = (endColor.red - startColor.red) / (n - 1)
        val colors = mutableListOf<Color>()
        for (i in 0 until n) {
            val red = startColor.red
            val green = startColor.green - i * step
            val blue = startColor.blue + i * step
            colors.add(Color(red, green, blue))
        }
        return colors
    } else if (type == "Chi") {
        // Phân bố đều từ đỏ sang vàng
        val startColor = Color(0xFF0000) // Đỏ
        val endColor = Color(0xFFFF00) // Vàng
        val step = (endColor.red - startColor.red) / (n - 1)
        val colors = mutableListOf<Color>()
        for (i in 0 until n) {
            val red = startColor.red + i * step
            val green = startColor.green
            val blue = startColor.blue
            colors.add(Color(red, green, blue))
        }
        return colors
    } else {
        // Xử lý trường hợp type không hợp lệ
        throw IllegalArgumentException("Type must be either \"Thu\" or \"Chi\"")
    }
}
