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
import sk.termin.booking.ui.OwnerScheduleScreen
import sk.termin.booking.ui.theme.TerminTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    private var appointments by mutableStateOf<List<Appointment>>(emptyList())
    private var selectedDate by mutableStateOf(LocalDate.now())
    private var workingStartHour by mutableStateOf(9)
    private var workingHours by mutableStateOf(8)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            appointments = DemoAppointmentRepository().getTodayAppointments()
        }
        setContent {
            TerminTheme {
                OwnerScheduleScreen(
                    appointments = appointments,
                    selectedDate = selectedDate,
                    workingStartHour = workingStartHour,
                    workingHours = workingHours,
                    onDateSelected = { selectedDate = it },
                    onWorkingStartHourChanged = { workingStartHour = it },
                    onWorkingHoursChanged = { workingHours = it },
                )
            }
        }
    }
}
