package sk.termin.booking.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.termin.booking.R
import sk.termin.booking.domain.Appointment
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val SlovakLocale = Locale.forLanguageTag("sk-SK")
private val TenMillimetres = 63.dp

@Composable
fun OwnerScheduleScreen(
    appointments: List<Appointment>,
    selectedDate: LocalDate,
    workingStartHour: Int,
    workingHours: Int,
    onDateSelected: (LocalDate) -> Unit,
    onWorkingStartHourChanged: (Int) -> Unit,
    onWorkingHoursChanged: (Int) -> Unit,
) {
    val year = selectedDate.year.toString()
    val monthName = selectedDate.month.getDisplayName(TextStyle.FULL, SlovakLocale)
        .replaceFirstChar { it.titlecase(SlovakLocale) }

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.parchment_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(start = 32.dp, top = TenMillimetres, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BusinessTitle()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 32.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                    Box(
                        modifier = Modifier.weight(0.72f).height(70.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = year,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontFamily = FontFamily.Cursive,
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                    Spacer(Modifier.weight(0.28f))
                    }

                    Spacer(Modifier.height(34.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ValueTile(monthName, Modifier.weight(1f), compact = true)
                        Spacer(Modifier.width(23.dp))
                        NextButton(
                            onClick = {
                                onDateSelected(selectedDate.withDayOfMonth(1).plusMonths(1))
                            },
                        )
                    }
            }
        }
    }
}

@Composable
private fun BusinessTitle() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth().height(94.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BarberPole()
            Text(
                text = "Barbershop",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontFamily = FontFamily.Cursive,
                fontSize = 52.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
            )
            BarberPole()
        }
        Text(
            text = "Stephano",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
private fun BarberPole(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.width(28.dp).height(82.dp)) {
        val outline = Color(0xFF1E1B1C)
        val silver = Color(0xFFE8DFC6)
        val body = Color(0xFFFFF8E7)
        val red = Color(0xFFB7352F)
        val blue = Color(0xFF245A7B)
        val left = size.width * 0.18f
        val right = size.width * 0.82f
        val top = size.height * 0.16f
        val bottom = size.height * 0.84f
        val width = right - left

        drawRoundRect(outline, Offset(left - 2.dp.toPx(), top - 3.dp.toPx()), Size(width + 4.dp.toPx(), bottom - top + 6.dp.toPx()), CornerRadius(5.dp.toPx()))
        drawRoundRect(body, Offset(left, top), Size(width, bottom - top), CornerRadius(3.dp.toPx()))
        clipRect(left, top, right, bottom) {
            repeat(4) { index ->
                val x = -size.width * 0.22f + index * size.width * 0.32f
                drawLine(red, Offset(x, bottom), Offset(x + size.width * 0.62f, top), strokeWidth = size.width * 0.18f)
                drawLine(blue, Offset(x + size.width * 0.12f, bottom), Offset(x + size.width * 0.74f, top), strokeWidth = size.width * 0.18f)
            }
        }
        drawRoundRect(outline, Offset(left, top), Size(width, bottom - top), CornerRadius(3.dp.toPx()), style = Stroke(1.dp.toPx()))
        drawRoundRect(outline, Offset(left - 5.dp.toPx(), top - 7.dp.toPx()), Size(width + 10.dp.toPx(), 9.dp.toPx()), CornerRadius(4.dp.toPx()))
        drawRoundRect(silver, Offset(left - 3.dp.toPx(), top - 5.dp.toPx()), Size(width + 6.dp.toPx(), 5.dp.toPx()), CornerRadius(3.dp.toPx()))
        drawRoundRect(outline, Offset(left - 5.dp.toPx(), bottom - 2.dp.toPx()), Size(width + 10.dp.toPx(), 9.dp.toPx()), CornerRadius(4.dp.toPx()))
        drawRoundRect(silver, Offset(left - 3.dp.toPx(), bottom), Size(width + 6.dp.toPx(), 5.dp.toPx()), CornerRadius(3.dp.toPx()))
    }
}

@Composable
private fun ValueTile(value: String, modifier: Modifier, compact: Boolean = false) {
    Box(
        modifier = modifier
            .height(if (compact) 66.dp else 70.dp)
            .border(
                border = BorderStroke(1.dp, Color.Black),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = value,
            modifier = Modifier.padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            style = if (compact) MaterialTheme.typography.headlineLarge else MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
private fun NextButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.width(46.dp).height(66.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
    ) {
        Text("›", style = MaterialTheme.typography.headlineMedium)
    }
}
