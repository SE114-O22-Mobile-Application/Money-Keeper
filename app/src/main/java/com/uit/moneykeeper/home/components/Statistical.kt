package com.uit.moneykeeper.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.uit.moneykeeper.global.GlobalObject
import com.uit.moneykeeper.home.viewmodel.colorListChi
import com.uit.moneykeeper.home.viewmodel.colorListThu
import com.uit.moneykeeper.home.viewmodel.filterData
import com.uit.moneykeeper.home.views.formatNumberWithCommas
import com.uit.moneykeeper.models.ViModel
import java.time.LocalDate

@Composable
fun Statistical(
    wallet: ViModel,
    date1: LocalDate,
    date2: LocalDate,
    option: String,
    typeOfTrans: String,
) {
    PieChartScreen(wallet, date1, date2, option, typeOfTrans)
}

@Composable
fun PieChartScreen(
    wallet: ViModel,
    date1: LocalDate,
    date2: LocalDate,
    option: String,
    typeOfTrans: String,
) {
    var transactions by remember { mutableStateOf(filterData(wallet, date1, date2, option, typeOfTrans)) }
    var totalCost by remember { mutableStateOf(transactions.sumOf { it.soTien }) }
    var selectedSlice by remember { mutableStateOf(PieChartData.Slice("Tổng", totalCost.toFloat(), Color.White)) }

    val colorList = if (typeOfTrans == "Thu") colorListThu else colorListChi
    val slices = remember { mutableStateListOf<PieChartData.Slice>() }

    // Function to update slices
    fun updateSlices() {
        val loaiGiaoDichTongTienMap = transactions.groupBy { it.loaiGiaoDich }
            .mapValues { (_, transactions) -> transactions.sumOf { it.soTien } }
        slices.clear()
        loaiGiaoDichTongTienMap.forEach { (loaiGiaoDich, tongTien) ->
            val color = colorList[slices.size % colorList.size]
            val percentage = (tongTien.toFloat() / totalCost * 100)
            val slice = PieChartData.Slice(loaiGiaoDich.ten + "(" + String.format("%.2f", percentage) + "%)", tongTien.toFloat(), color)
            slices.add(slice)
        }
    }

    // Initial update of slices
    LaunchedEffect(wallet, date1, date2, option, typeOfTrans) {
        transactions = filterData(wallet, date1, date2, option, typeOfTrans)
        totalCost = transactions.sumOf { it.soTien }
        selectedSlice = PieChartData.Slice("Tổng", totalCost.toFloat(), Color.White)
        updateSlices()
    }

    // PieChartData and PieChartConfig
    val pieChartData by remember { derivedStateOf { PieChartData(slices = slices, plotType = PlotType.Pie) } }
    val pieChartConfig by remember {
        mutableStateOf(
            PieChartConfig(
                isSumVisible = true,
                isAnimationEnable = true,
                showSliceLabels = true,
                animationDuration = 600,
                activeSliceAlpha = 0.5f
            )
        )
    }

    // UI components
    Column {
        if (transactions.isEmpty() || slices.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Không có dữ liệu",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        } else {
            PieChart(
                modifier = Modifier.fillMaxSize(),
                pieChartData = pieChartData,
                pieChartConfig = pieChartConfig,
                onSliceClick = { slice ->
                    selectedSlice = if (slice == selectedSlice) {
                        PieChartData.Slice("Tổng", totalCost.toFloat(), Color.White)
                    } else {
                        slice
                    }
                },
            )
            Text(
                text = "${selectedSlice.label}: ${formatNumberWithCommas(selectedSlice.value.toDouble())}",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = TextStyle(fontSize = 18.sp)
            )
        }
    }
}


fun Print(label: String) {
    println(label);
}