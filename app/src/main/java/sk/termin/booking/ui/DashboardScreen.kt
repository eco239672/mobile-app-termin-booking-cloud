package sk.termin.booking.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import sk.termin.booking.domain.Appointment
import sk.termin.booking.domain.AppointmentStatus
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DashboardScreen(appointments: List<Appointment>) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { Header() }
            item { Summary(appointments) }
            item { Text("Dnešný plán", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) }
            items(appointments, key = { it.id }) { appointment -> AppointmentCard(appointment) }
        }
    }
}

@Composable
private fun Header() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("termín", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Text("AK", modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer).padding(horizontal = 11.dp, vertical = 8.dp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(36.dp))
        Text("PREHĽAD", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Dnešné termíny", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("utorok, 27. októbra", color = Color(0xFF60727A), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(18.dp))
        Button(onClick = { }) { Text("+ Nový termín") }
    }
}

@Composable
private fun Summary(appointments: List<Appointment>) {
    val confirmed = appointments.count { it.status == AppointmentStatus.Confirmed }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        SummaryCard("Termíny", appointments.size.toString(), Modifier.weight(1f))
        SummaryCard("Potvrdené", confirmed.toString(), Modifier.weight(1f))
        SummaryCard("Čakajú", (appointments.size - confirmed).toString(), Modifier.weight(1f))
    }
}

@Composable
private fun SummaryCard(label: String, count: String, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(label, color = Color(0xFF60727A), style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(7.dp))
            Text(count, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun AppointmentCard(appointment: Appointment) {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.forLanguageTag("sk-SK"))
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(appointment.startsAt.format(formatter), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(17.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(appointment.customerName, fontWeight = FontWeight.Bold)
                Text("${appointment.service} · ${appointment.durationMinutes} min.", color = Color(0xFF60727A), style = MaterialTheme.typography.bodySmall)
            }
            val waiting = appointment.status == AppointmentStatus.Pending
            Text(
                if (waiting) "Čaká" else "Potvrdené",
                modifier = Modifier.clip(RoundedCornerShape(50)).background(if (waiting) Color(0xFFFFF0CE) else Color(0xFFE1F2E6)).padding(horizontal = 9.dp, vertical = 5.dp),
                color = if (waiting) Color(0xFF835B10) else Color(0xFF256642),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
