package sk.termin.booking.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.termin.booking.R
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

private val SlovakMonthTitles = mapOf(
    Month.JANUARY to "Január",
    Month.FEBRUARY to "Február",
    Month.MARCH to "Marec",
    Month.APRIL to "Apríl",
    Month.MAY to "Máj",
    Month.JUNE to "Jún",
    Month.JULY to "Júl",
    Month.AUGUST to "August",
    Month.SEPTEMBER to "September",
    Month.OCTOBER to "Október",
    Month.NOVEMBER to "November",
    Month.DECEMBER to "December",
)

private val SlovakLocale = Locale.forLanguageTag("sk-SK")

@Composable
fun MonthDaysScreen(displayedDate: LocalDate, onBack: () -> Unit) {
    val month = YearMonth.from(displayedDate)
    val dates = (1..month.lengthOfMonth()).map(month::atDay)
    val title = "${SlovakMonthTitles.getValue(month.month)} ${month.year}"

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.parchment_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 28.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                TextButton(onClick = onBack) {
                    Text("‹ Späť", color = Color.Black, fontFamily = FontFamily.Cursive, fontSize = 28.sp)
                }
            }
            item {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Normal,
                )
                Spacer(Modifier.height(18.dp))
            }
            items(dates, key = { it.toEpochDay() }) { date ->
                DayLine(date)
            }
        }
    }
}

@Composable
private fun DayLine(date: LocalDate) {
    val weekday = date.dayOfWeek.getDisplayName(TextStyle.FULL, SlovakLocale)
        .replaceFirstChar { it.titlecase(SlovakLocale) }
    val isPastDay = date.isBefore(LocalDate.now())
    val dayColor = if (isPastDay) Color.Black.copy(alpha = 0.38f) else Color.Black
    val dividerColor = if (isPastDay) Color.Black.copy(alpha = 0.14f) else Color.Black.copy(alpha = 0.28f)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = dayColor,
                fontFamily = FontFamily.Cursive,
                fontSize = 42.sp,
            )
            Spacer(Modifier.width(18.dp))
            Text(
                text = weekday,
                color = dayColor,
                fontFamily = FontFamily.Cursive,
                fontSize = 30.sp,
            )
        }
        HorizontalDivider(color = dividerColor)
    }
}
