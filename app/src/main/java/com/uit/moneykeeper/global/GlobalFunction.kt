package com.uit.moneykeeper.global

import androidx.compose.ui.graphics.Color
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.LoaiGiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.models.ViModel
import com.uit.moneykeeper.transaction.components.IconEnum
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import androidx.compose.ui.graphics.toArgb

object GlobalFunction {

    fun convertLocalDateToTimestamp(localDate: LocalDate): Timestamp {
        // Create a Date from the LocalDate at the start of the day
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        // Get the seconds and nanoseconds from the Date
        val seconds = date.time / 1000
        val nanoseconds = ((date.time % 1000) * 1000000).toInt()

        // Create a new Timestamp from the seconds and nanoseconds
        return Timestamp(seconds, nanoseconds)
    }

    fun convertTimestampToLocalDate(timestamp: Timestamp): LocalDate {
        // Create a Date from the Timestamp
        val date = Date(timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000)

        // Convert the Date to a LocalDate
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
    fun updateListGiaoDich(){
        val db = Firebase.firestore
        db.collection("giaoDich").get().addOnSuccessListener { result ->
            val list = result.mapNotNull { document ->
                document.get("loaiGiaoDich")?.let { map ->
                    (map as? Map<*, *>)?.let { loaiGiaoDichMap ->
                        val loaiGiaoDichString = loaiGiaoDichMap["loai"] as? String
                        val phanLoai = if (loaiGiaoDichString != null) {
                            try {
                                PhanLoai.valueOf(loaiGiaoDichString)
                            } catch (e: IllegalArgumentException) {
                                // Sử dụng giá trị mặc định nếu giá trị không hợp lệ
                                PhanLoai.Chi // Replace with your default enum value
                            }
                        } else {
                            // Sử dụng giá trị mặc định nếu giá trị là null
                            PhanLoai.Chi // Replace with your default enum value
                        }

                        LoaiGiaoDichModel(
                            loaiGiaoDichMap["id"] as? Int ?: 0,
                            loaiGiaoDichMap["ten"] as? String ?: "",
                            phanLoai,
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
                        convertTimestampToLocalDate(
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
            GlobalObject._listGiaoDich.value = list
        }
    }

    fun colorToString(color: Color): String {
        return "#${Integer.toHexString(color.toArgb())}"
    }

    fun stringToColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }
}