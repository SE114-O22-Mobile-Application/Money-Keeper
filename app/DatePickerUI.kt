package com.uit.moneykeeper.components
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.util.LocalePreferences
import kotlinx.coroutines.launch
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class MonthData(
    val name:String,
    val index:Int
)

enum class CalendarType {
    ONLY_MONTH,
    ONLY_YEAR,
    MONTH_AND_YEAR,
    ONE_SCREEN_MONTH_AND_YEAR
}
enum class MonthViewType {
    ONLY_MONTH,
    ONLY_NUMBER,
    ONLY_NUMBER_ONE_COLUMN,
    BOTH_NUMBER_AND_MONTH
}

@Composable
fun CalendarBottom(
    onPositiveClick: () -> Unit,
    onCancelClick: () -> Unit,
    themeColor: Color,
    negativeButtonTitle: String,
    positiveButtonTitle: String,
    buttonTextSize: TextUnit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = negativeButtonTitle,
            color = themeColor,
            fontSize = buttonTextSize,
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable { onCancelClick() }
        )
        Text(
            text = positiveButtonTitle,
            color = themeColor,
            fontSize = buttonTextSize,
            modifier = Modifier.clickable { onPositiveClick() }
        )
    }
}

@Composable
fun CalendarHeader(
    selectedMonth: MonthData,
    selectedYear: Int,
    showMonths: Boolean,
    setShowMonths: (Boolean) -> Unit,
    title: String,
    calendarType: CalendarType,
    themeColor: Color,
    monthViewType: MonthViewType?,
) {
    val monthAsNumber = String.format("%02d", selectedMonth.index.plus(1))
    val monthText = if (monthViewType == MonthViewType.ONLY_MONTH) selectedMonth.name.uppercase() else monthAsNumber

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White), // Changed to white background
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp),
            color = Color.Black // Changed text color to black for contrast
        )
        Row {
            if (calendarType != CalendarType.ONLY_YEAR) {
                Text(
                    text = monthText,
                    fontSize = 35.sp,
                    modifier = Modifier
                        .padding(
                            bottom = 20.dp,
                            start = if (calendarType == CalendarType.ONLY_MONTH) 0.dp else 30.dp,
                            end = if (calendarType == CalendarType.ONLY_MONTH) 0.dp else 10.dp
                        )
                        .clickable { setShowMonths(true) },
                    color = if (calendarType == CalendarType.ONE_SCREEN_MONTH_AND_YEAR) {
                        Color.Black // Changed to black for contrast
                    } else {
                        if (showMonths) Color.Black else Color.Gray
                    }
                )
            }
            if (calendarType != CalendarType.ONLY_MONTH && calendarType != CalendarType.ONLY_YEAR) {
                Text(text = "/", fontSize = 35.sp, color = Color.Black) // Changed to black for contrast
            }
            if (calendarType != CalendarType.ONLY_MONTH) {
                Text(
                    text = selectedYear.toString(),
                    fontSize = 35.sp,
                    modifier = Modifier
                        .padding(
                            bottom = 20.dp,
                            start = if (calendarType == CalendarType.ONLY_YEAR) 0.dp else 10.dp,
                            end = if (calendarType == CalendarType.ONLY_YEAR) 0.dp else 30.dp
                        )
                        .clickable { setShowMonths(false) },
                    color = if (calendarType == CalendarType.ONE_SCREEN_MONTH_AND_YEAR) {
                        Color.Black // Changed to black for contrast
                    } else {
                        if (showMonths) Color.Gray else Color.Black
                    }
                )
            }
        }
    }
}

@Composable
fun CalendarMonthView(
    monthList: List<MonthData>,
    selectedMonth: MonthData,
    setMonth: (MonthData) -> Unit,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
    unselectedColor: Color,
    monthViewType: MonthViewType?
) {

    val NUMBER_OF_ROW_ITEMS = 3
    var numberOfElement = 0

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.85f)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        items(items = monthList.chunked(NUMBER_OF_ROW_ITEMS)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for ((index, item) in rowItems.withIndex()) {
                    MonthItem(
                        month = item,
                        index = index,
                        numberOfElement = numberOfElement,
                        rowSize = NUMBER_OF_ROW_ITEMS,
                        selectedMonth = selectedMonth.name,
                        setMonth = setMonth,
                        minMonth = minMonth,
                        maxMonth = maxMonth,
                        minYear = minYear,
                        maxYear = maxYear,
                        selectedYear = selectedYear,
                        setShowMonths = setShowMonths,
                        showOnlyMonth = showOnlyMonth,
                        themeColor = themeColor,
                        unselectedColor = unselectedColor,
                        monthViewType = monthViewType
                    )
                    numberOfElement += 1
                }
            }
        }
    }
}

@Composable
fun MonthItem(
    month: MonthData,
    selectedMonth: String,
    setMonth: (MonthData) -> Unit,
    index: Int,
    numberOfElement: Int,
    rowSize: Int,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
    unselectedColor: Color,
    monthViewType: MonthViewType?
) {
    val enabled = checkDate(
        minYear = minYear,
        maxYear = maxYear,
        selectedYear = selectedYear,
        maxMonth = maxMonth,
        minMonth = minMonth,
        numberOfElement = numberOfElement
    )

    val monthAsNumber = String.format("%02d",numberOfElement.plus(1))

    val monthText: String = when (monthViewType) {
        MonthViewType.ONLY_MONTH -> month.name.uppercase()
        MonthViewType.ONLY_NUMBER -> monthAsNumber
        MonthViewType.BOTH_NUMBER_AND_MONTH -> month.name.uppercase() + " " + "(${
            monthAsNumber
        })"
        else -> month.name.uppercase()
    }
    Box(
        modifier = Modifier
            .background(
                color = if (month.name == selectedMonth) themeColor else Color.Transparent,
                shape = RoundedCornerShape(100)
            )
            .fillMaxWidth(1f / (rowSize - index + 1f))
            .aspectRatio(1f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                enabled = enabled
            ) {
                setMonth(month)
                if (!showOnlyMonth) {
                    setShowMonths(false)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = monthText,
            color = if (enabled && month.name == selectedMonth) Color.White
            else if (enabled) unselectedColor
            else Color.Gray
        )
    }
}

@Composable
fun CalendarMonthViewOneColumn(
    monthList: List<MonthData>,
    selectedMonth: MonthData,
    setMonth: (MonthData) -> Unit,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
    unselectedColor: Color,
    modifier: Modifier = Modifier
        .fillMaxHeight(0.85f)
        .fillMaxWidth()
        .padding(10.dp),
    monthViewType: MonthViewType?,
) {

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = monthList) { item ->
            MonthItemOneColumn(
                month = item,
                index = item.index,
                selectedMonth = selectedMonth.name,
                setMonth = setMonth,
                minMonth = minMonth,
                maxMonth = maxMonth,
                minYear = minYear,
                maxYear = maxYear,
                selectedYear = selectedYear,
                setShowMonths = setShowMonths,
                showOnlyMonth = showOnlyMonth,
                themeColor = themeColor,
                unselectedColor = unselectedColor,
                monthViewType = monthViewType
            )
        }
    }
}

@Composable
fun MonthItemOneColumn(
    month: MonthData,
    selectedMonth: String,
    setMonth: (MonthData) -> Unit,
    index: Int,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
    unselectedColor: Color,
    monthViewType: MonthViewType?,
) {
    val enabled = checkDate(
        minYear = minYear,
        maxYear = maxYear,
        selectedYear = selectedYear,
        maxMonth = maxMonth,
        minMonth = minMonth,
        numberOfElement = index
    )

    val monthAsNumber = String.format("%02d", index.plus(1))

    val monthText: String = when (monthViewType) {
        MonthViewType.ONLY_MONTH -> month.name.uppercase()
        MonthViewType.ONLY_NUMBER -> monthAsNumber
        MonthViewType.BOTH_NUMBER_AND_MONTH -> month.name.uppercase() + " " + "(${
            monthAsNumber
        })"
        else -> month.name.uppercase()
    }

    Box(modifier = Modifier
        .padding(vertical = 6.dp)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = enabled
        ) {
            setMonth(month)
            if (!showOnlyMonth) {
                setShowMonths(false)
            }
        }) {
        Text(
            text = monthText,
            fontSize = if (month.name == selectedMonth) 35.sp else 30.sp,
            color = if (enabled && month.name == selectedMonth) themeColor
            else if (enabled) unselectedColor
            else Color.Gray
        )
    }
}

private fun checkDate(
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    minMonth: Int,
    maxMonth: Int,
    numberOfElement: Int,
): Boolean {

    if (minYear == maxYear) return numberOfElement in minMonth..maxMonth
    if (selectedYear == minYear) {
        return numberOfElement >= minMonth
    } else if (selectedYear == maxYear) {
        if (numberOfElement > maxMonth) return false
    }
    return true
}
@Composable
fun CalendarYearView(
    selectedYear: Int,
    setYear: (Int) -> Unit,
    minYear: Int,
    maxYear: Int,
    themeColor: Color
) {
    val years = IntRange(minYear, maxYear).toList()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val selectedIndex = years.indexOf(selectedYear)

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxHeight(0.85f)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(years) { year ->
            Text(text = year.toString(),
                fontSize = if (year == selectedYear) 35.sp else 30.sp,
                color = if (year == selectedYear) themeColor else Color.Black,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        setYear(year)
                    })
        }
        scope.launch {
            listState.animateScrollToItem(selectedIndex)
        }
    }
}
@Composable
fun ComposeCalendar(
    minDate: Date? = null,
    maxDate: Date? = null,
    initialDate: Date? = null,
    locale: Locale = Locale.getDefault(),
    title: String = "",
    listener: SelectDateListener,
    calendarType: CalendarType = CalendarType.MONTH_AND_YEAR,
    themeColor: Color = Color(0xFF614FF0),
    unselectedColor: Color = Color.Black,
    negativeButtonTitle: String = "CANCEL",
    positiveButtonTitle: String = "OK",
    buttonTextSize: TextUnit = TextUnit.Unspecified,
    monthViewType: MonthViewType? = MonthViewType.ONLY_MONTH
) {

    var minYear = 1970
    var minMonth = 0
    var maxYear = 2100
    var maxMonth = 11

    var initialCalendar = Calendar.getInstance(locale)
    var currentMonth = initialCalendar.get(Calendar.MONTH)
    var currentYear = initialCalendar.get(Calendar.YEAR)

    minDate?.let {
        val calendarMin = Calendar.getInstance()
        calendarMin.time = minDate
        minMonth = calendarMin.get(Calendar.MONTH)
        minYear = calendarMin.get(Calendar.YEAR)
    }
    maxDate?.let {
        val calendarMax = Calendar.getInstance()
        calendarMax.time = maxDate
        maxMonth = calendarMax.get(Calendar.MONTH)
        maxYear = calendarMax.get(Calendar.YEAR)
    }
    initialDate?.let {
        initialCalendar.time = initialDate
        currentMonth = initialCalendar.get(Calendar.MONTH)
        currentYear = initialCalendar.get(Calendar.YEAR)
    }


    if (minYear > currentYear) {
        currentYear = minYear
    }
    if (maxYear < currentYear) {
        currentYear = maxYear
    }

    val months = (DateFormatSymbols(locale).shortMonths).toList()
    val monthList = months.mapIndexed { index, name ->
        MonthData(name = name, index = index)
    }
    val (selectedMonth, setMonth) = remember {
        mutableStateOf(
            MonthData(
                name = DateFormatSymbols(locale).shortMonths[currentMonth],
                index = currentMonth
            )
        )
    }
    val (selectedYear, setYear) = remember {
        mutableStateOf(currentYear)
    }
    val (showMonths, setShowMonths) = remember {
        mutableStateOf(
            calendarType != CalendarType.ONLY_YEAR
        )
    }

    val calendarDate = Calendar.getInstance()
    var selectedDate by remember {
        mutableStateOf(calendarDate.time)
    }

    LaunchedEffect(key1 = selectedYear, key2 = selectedMonth) {
        calendarDate.set(Calendar.YEAR, selectedYear)
        calendarDate.set(Calendar.MONTH, selectedMonth.index)
        calendarDate.set(
            Calendar.DAY_OF_MONTH,
            1
        ) //For example, if the date is 30 march and we click february, then it shows 2March because february does not have 30 days, so we set default day to 1
        selectedDate = calendarDate.time
    }
    LaunchedEffect(key1 = selectedYear) {
        if (selectedYear == minYear) {
            if (selectedMonth.index < minMonth) {
                setMonth(monthList[minMonth])
            }
        }
        if (selectedYear == maxYear) {
            if (selectedMonth.index > maxMonth) {
                setMonth(monthList[maxMonth])
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CalendarHeader(
                selectedMonth = selectedMonth,
                selectedYear = selectedYear,
                showMonths = showMonths,
                setShowMonths = setShowMonths,
                title = title,
                calendarType = calendarType,
                themeColor = themeColor,
                monthViewType = monthViewType
            )
            if (calendarType == CalendarType.ONE_SCREEN_MONTH_AND_YEAR) {
                OneScreenMonthYear(
                    selectedMonth = selectedMonth,
                    setMonth = setMonth,
                    minMonth = minMonth,
                    maxMonth = maxMonth,
                    setShowMonths = setShowMonths,
                    minYear = minYear,
                    maxYear = maxYear,
                    selectedYear = selectedYear,
                    monthList = monthList,
                    showOnlyMonth = calendarType == CalendarType.ONLY_MONTH,
                    themeColor = themeColor,
                    unselectedColor = unselectedColor,
                    setYear = setYear,
                    monthViewType = monthViewType
                )
            } else {
                Crossfade(targetState = showMonths) {
                    when (it) {
                        true -> {
                            if (monthViewType == MonthViewType.ONLY_NUMBER_ONE_COLUMN) {
                                CalendarMonthViewOneColumn(
                                    selectedMonth = selectedMonth,
                                    setMonth = setMonth,
                                    minMonth = minMonth,
                                    maxMonth = maxMonth,
                                    setShowMonths = setShowMonths,
                                    minYear = minYear,
                                    maxYear = maxYear,
                                    selectedYear = selectedYear,
                                    monthList = monthList,
                                    showOnlyMonth = calendarType == CalendarType.ONLY_MONTH,
                                    themeColor = themeColor,
                                    unselectedColor = unselectedColor,
                                    monthViewType = monthViewType
                                )
                            } else {
                                CalendarMonthView(
                                    selectedMonth = selectedMonth,
                                    setMonth = setMonth,
                                    minMonth = minMonth,
                                    maxMonth = maxMonth,
                                    setShowMonths = setShowMonths,
                                    minYear = minYear,
                                    maxYear = maxYear,
                                    selectedYear = selectedYear,
                                    monthList = monthList,
                                    showOnlyMonth = calendarType == CalendarType.ONLY_MONTH,
                                    themeColor = themeColor,
                                    unselectedColor = unselectedColor,
                                    monthViewType = monthViewType
                                )
                            }
                        }
                        false -> CalendarYearView(
                            selectedYear = selectedYear,
                            setYear = setYear,
                            minYear = minYear,
                            maxYear = maxYear,
                            themeColor = themeColor
                        )
                    }
                }
            }
            CalendarBottom(
                onPositiveClick = { listener.onDateSelected(selectedDate) },
                onCancelClick = { listener.onCanceled() },
                themeColor = themeColor,
                buttonTextSize = buttonTextSize,
                negativeButtonTitle = negativeButtonTitle,
                positiveButtonTitle = positiveButtonTitle
            )
        }
    }
}



@Composable
fun OneScreenMonthYear(
    monthList: List<MonthData>,
    selectedMonth: MonthData,
    setMonth: (MonthData) -> Unit,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
    unselectedColor: Color,
    setYear: (Int) -> Unit,
    monthViewType: MonthViewType?,
) {
    Row() {
        CalendarMonthViewOneColumn(
            monthList = monthList,
            selectedMonth = selectedMonth,
            setMonth = setMonth,
            minMonth = minMonth,
            maxMonth = maxMonth,
            minYear = minYear,
            maxYear = maxYear,
            selectedYear = selectedYear,
            setShowMonths = setShowMonths,
            showOnlyMonth = showOnlyMonth,
            themeColor = themeColor,
            unselectedColor = unselectedColor,
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth(0.5f)
                .padding(vertical = 10.dp),
            monthViewType = monthViewType,
        )
        CalendarYearView(
            selectedYear = selectedYear,
            setYear = setYear,
            minYear = minYear,
            maxYear = maxYear,
            themeColor = themeColor
        )
    }
}
interface SelectDateListener {
    fun onDateSelected(date: Date)
    fun onCanceled()
}