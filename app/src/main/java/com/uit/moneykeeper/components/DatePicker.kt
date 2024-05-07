import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import android.app.DatePickerDialog
import androidx.compose.runtime.mutableStateOf
import java.util.*

@Composable
fun DatePickerComponent(selectedDate: MutableState<Date?>) {
    val context = LocalContext.current
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val text = selectedDate.value?.let { dateFormatter.format(it) } ?: ""

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { },
            label = { Text("Chọn ngày") },
            readOnly = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Hide keyboard */ }),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                val calendar = Calendar.getInstance()
                selectedDate.value = calendar.time

                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)
                        selectedDate.value = calendar.time
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        ) {
            Text("Chọn ngày")
        }
    }
}

@Preview
@Composable
fun PreviewDatePickerComponent() {
    MaterialTheme {
        val selectedDate = remember { mutableStateOf<Date?>(null) }
        DatePickerComponent(selectedDate = selectedDate)
    }
}
