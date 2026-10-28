package sk.termin.booking.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.termin.booking.R
import sk.termin.booking.domain.Appointment
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val SlovakLocale = Locale.forLanguageTag("sk-SK")

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
                BarberFlourish(top = true)
                Text(
                    text = "Barbershop\nStephano",
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Normal,
                )
                BarberFlourish(top = false)

                    Spacer(Modifier.height(40.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                        ValueTile(year.take(2), Modifier.weight(1f))
                        ValueTile(year.drop(2), Modifier.weight(1f))
                        Spacer(Modifier.width(23.dp))
                        NextButton(
                            onClick = {
                                onDateSelected(selectedDate.plusYears(1))
                            },
                        )
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
private fun BarberFlourish(top: Boolean) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(26.dp)
            .padding(horizontal = 34.dp),
    ) {
        val lineColor = Color(0xFF7D4D31)
        val stroke = Stroke(width = 1.6.dp.toPx(), cap = StrokeCap.Round)

        val flourish = Path().apply {
            if (top) {
                moveTo(size.width * 0.12f, size.height * 0.72f)
                cubicTo(size.width * 0.25f, size.height * 0.08f, size.width * 0.52f, size.height * 0.10f, size.width * 0.62f, size.height * 0.52f)
                cubicTo(size.width * 0.73f, size.height * 0.93f, size.width * 0.90f, size.height * 0.64f, size.width * 0.87f, size.height * 0.18f)
                cubicTo(size.width * 0.86f, size.height * 0.04f, size.width * 0.79f, size.height * 0.10f, size.width * 0.80f, size.height * 0.27f)
            } else {
                moveTo(size.width * 0.88f, size.height * 0.28f)
                cubicTo(size.width * 0.75f, size.height * 0.92f, size.width * 0.49f, size.height * 0.90f, size.width * 0.39f, size.height * 0.48f)
                cubicTo(size.width * 0.29f, size.height * 0.07f, size.width * 0.11f, size.height * 0.36f, size.width * 0.14f, size.height * 0.81f)
                cubicTo(size.width * 0.15f, size.height * 0.95f, size.width * 0.22f, size.height * 0.89f, size.width * 0.21f, size.height * 0.72f)
            }
        }
        val secondCurl = Path().apply {
            if (top) {
                moveTo(size.width * 0.12f, size.height * 0.72f)
                cubicTo(size.width * 0.00f, size.height * 0.95f, size.width * 0.00f, size.height * 0.22f, size.width * 0.10f, size.height * 0.28f)
                cubicTo(size.width * 0.19f, size.height * 0.33f, size.width * 0.17f, size.height * 0.75f, size.width * 0.12f, size.height * 0.72f)
            } else {
                moveTo(size.width * 0.88f, size.height * 0.28f)
                cubicTo(size.width * 1.00f, size.height * 0.05f, size.width * 1.00f, size.height * 0.78f, size.width * 0.90f, size.height * 0.72f)
                cubicTo(size.width * 0.81f, size.height * 0.67f, size.width * 0.83f, size.height * 0.25f, size.width * 0.88f, size.height * 0.28f)
            }
        }
        val centreAccent = Path().apply {
            if (top) {
                moveTo(size.width * 0.52f, size.height * 0.25f)
                cubicTo(size.width * 0.57f, size.height * 0.27f, size.width * 0.60f, size.height * 0.39f, size.width * 0.62f, size.height * 0.52f)
            } else {
                moveTo(size.width * 0.48f, size.height * 0.75f)
                cubicTo(size.width * 0.43f, size.height * 0.73f, size.width * 0.40f, size.height * 0.61f, size.width * 0.39f, size.height * 0.48f)
            }
        }

        drawPath(flourish, color = lineColor, style = stroke)
        drawPath(secondCurl, color = lineColor, style = stroke)
        drawPath(centreAccent, color = lineColor, style = Stroke(width = 2.6.dp.toPx(), cap = StrokeCap.Round))
    }
}

@Composable
private fun ValueTile(value: String, modifier: Modifier, compact: Boolean = false) {
    Card(
        modifier = modifier.height(if (compact) 66.dp else 70.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Text(
            text = value,
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
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
