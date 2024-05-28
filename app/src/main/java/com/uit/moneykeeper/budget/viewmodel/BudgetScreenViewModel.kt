package com.uit.moneykeeper.budget.viewmodel

fun formatNumberWithCommas(doubleValue: Double): String {
    val formattedValue = String.format("%.2f", doubleValue) // Format với hai số sau dấu phẩy
    val parts = formattedValue.split('.') // Tách phần nguyên và phần thập phân

    var integerPart = parts[0] // Phần nguyên
    var decimalPart = parts.getOrElse(1) { "" } // Phần thập phân, mặc định là chuỗi trống nếu không có phần thập phân

    // Đổi mỗi 3 chữ số của phần nguyên thành một dấu chấm
    val integerLength = integerPart.length
    var index = 0
    while (integerLength - index > 3) {
        integerPart = integerPart.substring(0, integerLength - 3 - index) + "." + integerPart.substring(integerLength - 3 - index)
        index += 3
    }

    // Xóa các số 0 không cần thiết sau dấu phẩy nếu có
    while (decimalPart.isNotEmpty() && decimalPart.last() == '0') {
        decimalPart = decimalPart.dropLast(1)
    }

    // Nếu phần thập phân không còn chữ số nào thì không cần hiển thị dấu phẩy
    if (decimalPart.isEmpty()) {
        return integerPart
    }

    return "$integerPart,$decimalPart"
}