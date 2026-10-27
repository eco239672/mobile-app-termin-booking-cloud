package sk.termin.booking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sk.termin.booking.domain.Appointment
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val SlovakLocale = Locale.forLanguageTag("sk-SK")
private val WeekdayLabels = listOf("P", "U", "S", "Š", "P", "S", "N")

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
    val months = (1..12).map { YearMonth.of(selectedDate.year, it) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { OwnerHeader() }
            item {
                Text(
                    text = "Ročný kalendár ${selectedDate.year}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
            items(months, key = { it.monthValue }) { month ->
                MonthCalendar(
                    month = month,
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item {
                WorkingDaySettings(
                    selectedDate = selectedDate,
                    workingStartHour = workingStartHour,
                    workingHours = workingHours,
                    onWorkingStartHourChanged = onWorkingStartHourChanged,
                    onWorkingHoursChanged = onWorkingHoursChanged,
                )
            }
            item {
                DailySlots(
                    appointments = appointments,
                    selectedDate = selectedDate,
                    workingStartHour = workingStartHour,
                    workingHours = workingHours,
                )
            }
        }
    }
}

@Composable
private fun OwnerHeader() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "termín",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "MAJITEĽ",
                modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer).padding(horizontal = 10.dp, vertical = 7.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        Spacer(Modifier.height(28.dp))
        Text("MOJ KALENDÁR", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text("Termíny pre váš podnik", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text("Vyberte deň a nastavte pracovný čas.", color = Color(0xFF60727A), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun MonthCalendar(
    month: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val monthName = month.atDay(1).month.getDisplayName(TextStyle.FULL, SlovakLocale).replaceFirstChar { it.titlecase(SlovakLocale) }
    val firstDayOffset = month.atDay(1).dayOfWeek.value - 1

    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(9.dp)) {
            Text(monthName, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(7.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                WeekdayLabels.forEach { label ->
                    Text(label, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color(0xFF718187), style = MaterialTheme.typography.labelSmall)
                }
            }
            repeat(6) { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(7) { weekday ->
                        val dayNumber = week * 7 + weekday - firstDayOffset + 1
                        if (dayNumber in 1..month.lengthOfMonth()) {
                            val date = month.atDay(dayNumber)
                            val isSelected = date == selectedDate
                            Text(
                                text = dayNumber.toString(),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(24.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                                    .clickable { onDateSelected(date) }
                                    .padding(top = 4.dp),
                                textAlign = TextAlign.Center,
                                color = if (isSelected) Color.White else Color(0xFF25383F),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            )
                        } else {
                            Spacer(Modifier.weight(1f).height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkingDaySettings(
    selectedDate: LocalDate,
    workingStartHour: Int,
    workingHours: Int,
    onWorkingStartHourChanged: (Int) -> Unit,
    onWorkingHoursChanged: (Int) -> Unit,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM", SlovakLocale)
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text(selectedDate.format(dateFormatter).replaceFirstChar { it.titlecase(SlovakLocale) }, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Nastavenie pracovného dňa", color = Color(0xFF60727A), style = MaterialTheme.typography.bodyMedium)
            TimeStepper(
                label = "Začiatok práce",
                value = "%02d:00".format(workingStartHour),
                onMinus = { onWorkingStartHourChanged((workingStartHour - 1).coerceAtLeast(5)) },
                onPlus = { onWorkingStartHourChanged((workingStartHour + 1).coerceAtMost(23 - workingHours)) },
            )
            TimeStepper(
                label = "Dĺžka pracovného dňa",
                value = "$workingHours hod.",
                onMinus = { onWorkingHoursChanged((workingHours - 1).coerceAtLeast(4)) },
                onPlus = { onWorkingHoursChanged((workingHours + 1).coerceAtMost(23 - workingStartHour)) },
            )
        }
    }
}

@Composable
private fun TimeStepper(label: String, value: String, onMinus: () -> Unit, onPlus: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        OutlinedButton(onClick = onMinus, contentPadding = PaddingValues(horizontal = 13.dp, vertical = 3.dp)) { Text("−") }
        Text(value, modifier = Modifier.width(68.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        OutlinedButton(onClick = onPlus, contentPadding = PaddingValues(horizontal = 13.dp, vertical = 3.dp)) { Text("+") }
    }
}

@Composable
private fun DailySlots(
    appointments: List<Appointment>,
    selectedDate: LocalDate,
    workingStartHour: Int,
    workingHours: Int,
) {
    val dateAppointments = appointments.filter { it.startsAt.toLocalDate() == selectedDate }

    Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
        Text("Dostupnosť", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text("Každý blok predstavuje jednu hodinu pracovného času.", color = Color(0xFF60727A), style = MaterialTheme.typography.bodySmall)
        repeat(workingHours) { index ->
            val hour = workingStartHour + index
            val appointment = dateAppointments.firstOrNull { it.startsAt.hour == hour }
            val isBooked = appointment != null
            Card(colors = CardDefaults.cardColors(containerColor = if (isBooked) Color(0xFFDCEFF2) else Color.White)) {
                Row(modifier = Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("%02d:00".format(hour), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(18.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(if (isBooked) appointment.customerName else "Voľný termín", fontWeight = FontWeight.Bold)
                        Text(if (isBooked) appointment.service else "K dispozícii pre zákazníka", color = Color(0xFF60727A), style = MaterialTheme.typography.bodySmall)
                    }
                    Text(if (isBooked) "Obsadené" else "Voľné", color = if (isBooked) Color(0xFF17687B) else Color(0xFF257148), style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
