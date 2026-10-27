package sk.termin.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import sk.termin.booking.data.DemoAppointmentRepository
import sk.termin.booking.domain.Appointment
import sk.termin.booking.ui.DashboardScreen
import sk.termin.booking.ui.theme.TerminTheme

class MainActivity : ComponentActivity() {
    private var appointments by mutableStateOf<List<Appointment>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            appointments = DemoAppointmentRepository().getTodayAppointments()
        }
        setContent {
            TerminTheme { DashboardScreen(appointments) }
        }
    }
}
