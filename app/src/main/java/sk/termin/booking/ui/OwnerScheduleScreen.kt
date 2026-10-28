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
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    ) {
                        Text(
                            text = "Barbershop\nStephano",
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Normal,
                        )
                        BarberFlourish()
                    }

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
private fun BarberFlourish() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .padding(horizontal = 22.dp),
    ) {
        val lineColor = Color(0xFF7D4D31)
        val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
        val midY = size.height * 0.52f

        val left = Path().apply {
            moveTo(size.width * 0.05f, midY)
            cubicTo(size.width * 0.13f, size.height * 0.10f, size.width * 0.26f, size.height * 0.10f, size.width * 0.31f, midY)
            cubicTo(size.width * 0.35f, size.height * 0.85f, size.width * 0.41f, size.height * 0.85f, size.width * 0.45f, midY)
            cubicTo(size.width * 0.41f, size.height * 0.35f, size.width * 0.35f, size.height * 0.35f, size.width * 0.34f, midY)
        }
        val right = Path().apply {
            moveTo(size.width * 0.95f, midY)
            cubicTo(size.width * 0.87f, size.height * 0.10f, size.width * 0.74f, size.height * 0.10f, size.width * 0.69f, midY)
            cubicTo(size.width * 0.65f, size.height * 0.85f, size.width * 0.59f, size.height * 0.85f, size.width * 0.55f, midY)
            cubicTo(size.width * 0.59f, size.height * 0.35f, size.width * 0.65f, size.height * 0.35f, size.width * 0.66f, midY)
        }

        drawPath(left, color = lineColor, style = stroke)
        drawPath(right, color = lineColor, style = stroke)
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
