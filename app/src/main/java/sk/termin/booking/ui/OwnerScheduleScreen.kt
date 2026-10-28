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
        val stroke = Stroke(width = 1.45.dp.toPx(), cap = StrokeCap.Round)
        fun y(value: Float) = if (top) size.height * value else size.height * (1f - value)

        val flourish = Path().apply {
            moveTo(size.width * 0.05f, y(0.70f))
            cubicTo(size.width * 0.00f, y(0.96f), size.width * 0.00f, y(0.25f), size.width * 0.12f, y(0.29f))
            cubicTo(size.width * 0.23f, y(0.34f), size.width * 0.24f, y(0.77f), size.width * 0.43f, y(0.55f))
            cubicTo(size.width * 0.59f, y(0.35f), size.width * 0.70f, y(0.65f), size.width * 0.83f, y(0.51f))
            cubicTo(size.width * 0.94f, y(0.40f), size.width * 0.98f, y(0.04f), size.width * 0.86f, y(0.10f))
            cubicTo(size.width * 0.77f, y(0.15f), size.width * 0.78f, y(0.41f), size.width * 0.88f, y(0.34f))
        }
        val centreAccent = Path().apply {
            moveTo(size.width * 0.31f, y(0.67f))
            cubicTo(size.width * 0.39f, y(0.72f), size.width * 0.47f, y(0.49f), size.width * 0.57f, y(0.46f))
        }

        drawPath(flourish, color = lineColor, style = stroke)
        drawPath(centreAccent, color = lineColor, style = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round))
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
