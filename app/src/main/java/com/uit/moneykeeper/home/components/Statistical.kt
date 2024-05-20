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
    val transactions = filterData(wallet, date1, date2, option, typeOfTrans);
    val totalCost = transactions.sumOf { it.soTien }
    val totalData = PieChartData.Slice("Tổng",totalCost.toFloat(),Color.White);
    var selectedSlice by remember { mutableStateOf(totalData) }
    println("---------------------------");
    println(wallet.ten);
    println("Totals: " + totalData)
    println("Slices: " + selectedSlice)
    if(transactions.size < 1) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Không có dữ liệu",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                style = TextStyle(
                    fontSize = 18.sp)
            )
        }
        return
    }

    val loaiGiaoDichTongTienMap = transactions.groupBy { it.loaiGiaoDich }
        .mapValues { (_, transactions) -> transactions.sumOf { it.soTien } }
    val slices = mutableListOf<PieChartData.Slice>()
    var cnt = transactions.size ;
    println(typeOfTrans);
    for (transaction in transactions) {
        println(transaction.id);
    }

    val colorList: List<Color>;
    if(typeOfTrans == "Thu")  colorList = colorListThu;
    else colorList = colorListChi;
    var iColor = 0;
    slices.clear();
    loaiGiaoDichTongTienMap.forEach { (loaiGiaoDich, tongTien) ->
        val slice = PieChartData.Slice(loaiGiaoDich.ten, tongTien.toFloat(), colorList[iColor])
        slices.add(slice)
        iColor = (iColor + 1) % 10;
    }

    val pieChartData = PieChartData(
        slices = slices,
        plotType = PlotType.Pie
    )
    println("Slices - i: " + pieChartData.slices);
    val pieChartConfig = PieChartConfig(
        isSumVisible = true,
        isAnimationEnable = true,
        showSliceLabels = true,
        animationDuration = 600,
        activeSliceAlpha = 0.5f
    )
    Column(
    ) {
        PieChart(
            modifier = Modifier
                .fillMaxSize(),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig,
            onSliceClick = {slice ->
                if(slice != selectedSlice)
                    selectedSlice = slice
                else
                    selectedSlice = totalData
                Print("abc")
            },

            )
        Text(
            text = "${selectedSlice.label}: ${formatNumberWithCommas(selectedSlice.value.toDouble())}",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 18.sp)
        )
    }
}

fun Print(label: String) {
    println(label);
}