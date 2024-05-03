package com.uit.moneykeeper.transaction.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uit.moneykeeper.models.GiaoDichModel
import com.uit.moneykeeper.models.PhanLoai
import com.uit.moneykeeper.transaction.viewmodel.DailyListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyList(
    giaoDichList: List<GiaoDichModel>
) {
    val sortedGiaoDichList = giaoDichList.sortedByDescending { it.ngayGiaoDich }

    val groupedGiaoDichList = sortedGiaoDichList.groupBy { it.ngayGiaoDich }

    val dailyItemList = groupedGiaoDichList.map { (date, transactions) ->
        Pair(date, transactions)
    }

    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    )  {
        dailyItemList.forEach { dailyItem ->
            DailyItem(dailyItem.first, dailyItem.second)
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
