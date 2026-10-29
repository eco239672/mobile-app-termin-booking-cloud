package sk.termin.booking.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.remember
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
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
import sk.termin.booking.BuildConfig
import sk.termin.booking.R
import sk.termin.booking.domain.Appointment
import java.time.LocalDate
import java.time.Month

private val TenMillimetres = 63.dp
private val SlovakMonthNames = mapOf(
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

@Composable
fun OwnerScheduleScreen(
    appointments: List<Appointment>,
    selectedDate: LocalDate,
    workingStartHour: Int,
    workingHours: Int,
    onDateSelected: (LocalDate) -> Unit,
    onWorkingStartHourChanged: (Int) -> Unit,
    onWorkingHoursChanged: (Int) -> Unit,
    onOpenMonth: () -> Unit,
) {
    val year = selectedDate.year.toString()
    val monthName = SlovakMonthNames.getValue(selectedDate.month)

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
                .padding(start = 32.dp, top = 246.dp, end = 32.dp, bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(132.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = year,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 117.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }

                    Spacer(Modifier.height(34.dp))

                Box(
                    modifier = Modifier.fillMaxWidth().height(78.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = monthName,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MonthArrowButton(
                        symbol = "‹",
                        onClick = {
                            onDateSelected(selectedDate.withDayOfMonth(1).minusMonths(1))
                        },
                    )
                    Spacer(Modifier.weight(1f))
                    MonthArrowButton(
                        symbol = "›",
                        onClick = {
                            onDateSelected(selectedDate.withDayOfMonth(1).plusMonths(1))
                        },
                    )
                }
                Spacer(Modifier.height(8.dp))
                OpenLabel(onClick = onOpenMonth)
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
            fontFamily = FontFamily.Cursive,
            fontSize = 54.sp,
            fontWeight = FontWeight.Normal,
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = if (BuildConfig.FLAVOR == "owner") "Booked appointments" else "Book now...",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = FontFamily.Cursive,
            fontSize = 39.sp,
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
private fun MonthArrowButton(symbol: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Box(
        modifier = Modifier
            .size(73.dp)
            .drawBehind {
                if (isPressed) {
                    val glowRadius = 73.dp.toPx()
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.324f), Color.Black.copy(alpha = 0.108f), Color.Transparent),
                            center = center,
                            radius = glowRadius,
                        ),
                        radius = glowRadius,
                    )
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(symbol, color = Color.Black, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun OpenLabel(onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(64.dp)
            .drawBehind {
                if (isPressed) {
                    val glowRadius = 73.dp.toPx()
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Black.copy(alpha = 0.324f), Color.Black.copy(alpha = 0.108f), Color.Transparent),
                            center = center,
                            radius = glowRadius,
                        ),
                        radius = glowRadius,
                    )
                }
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Open",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontFamily = FontFamily.Cursive,
            fontSize = 39.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}
