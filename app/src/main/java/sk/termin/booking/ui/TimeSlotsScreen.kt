package sk.termin.booking.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.termin.booking.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val TimeSlotLocale = Locale.forLanguageTag("sk-SK")

@Composable
fun TimeSlotsScreen(
    date: LocalDate,
    onBack: () -> Unit,
    onTimeSelected: (String) -> Unit,
) {
    val dateTitle = "${date.dayOfMonth}. ${date.month.getDisplayName(TextStyle.FULL, TimeSlotLocale)} ${date.year}"
    val timeSlots = (8..19).map { hour -> "%02d:00".format(hour) }

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
                    text = dateTitle,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 40.sp,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Available times",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 26.sp,
                )
                Spacer(Modifier.height(16.dp))
            }
            items(timeSlots) { time ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTimeSelected(time) }
                        .padding(vertical = 10.dp),
                ) {
                    Text(
                        text = time,
                        color = Color.Black,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 36.sp,
                    )
                    HorizontalDivider(color = Color.Black.copy(alpha = 0.28f))
                }
            }
        }
    }
}
