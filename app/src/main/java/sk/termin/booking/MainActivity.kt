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
import sk.termin.booking.ui.BookingRequestScreen
import sk.termin.booking.ui.MonthDaysScreen
import sk.termin.booking.ui.OwnerScheduleScreen
import sk.termin.booking.ui.TimeSlotsScreen
import sk.termin.booking.ui.theme.TerminTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    private var appointments by mutableStateOf<List<Appointment>>(emptyList())
    private var selectedDate by mutableStateOf(LocalDate.now())
    private var workingStartHour by mutableStateOf(9)
    private var workingHours by mutableStateOf(8)
    private var isMonthOpen by mutableStateOf(false)
    private var selectedBookingDate by mutableStateOf<LocalDate?>(null)
    private var selectedBookingTime by mutableStateOf<String?>(null)
    private var bookedSlots by mutableStateOf<Set<String>>(emptySet())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            appointments = DemoAppointmentRepository().getTodayAppointments()
        }
        setContent {
            TerminTheme {
                if (isMonthOpen) {
                    val bookingDate = selectedBookingDate
                    if (bookingDate != null) {
                        val bookingTime = selectedBookingTime
                        if (bookingTime != null) {
                            BookingRequestScreen(
                                date = bookingDate,
                                time = bookingTime,
                                onBack = { selectedBookingTime = null },
                                onSubmit = {
                                    bookedSlots = bookedSlots + bookingSlotKey(bookingDate, bookingTime)
                                },
                            )
                        } else {
                            TimeSlotsScreen(
                                date = bookingDate,
                                onBack = { selectedBookingDate = null },
                                onTimeSelected = { time -> selectedBookingTime = time },
                                bookedTimes = bookedSlots
                                    .filter { it.startsWith("$bookingDate|") }
                                    .map { it.substringAfter('|') }
                                    .toSet(),
                            )
                        }
                    } else {
                        MonthDaysScreen(
                            displayedDate = selectedDate,
                            onBack = { isMonthOpen = false },
                            onDateSelected = { date ->
                                selectedDate = date
                                selectedBookingDate = date
                            },
                        )
                    }
                } else {
                    OwnerScheduleScreen(
                        appointments = appointments,
                        selectedDate = selectedDate,
                        workingStartHour = workingStartHour,
                        workingHours = workingHours,
                        onDateSelected = { selectedDate = it },
                        onWorkingStartHourChanged = { workingStartHour = it },
                        onWorkingHoursChanged = { workingHours = it },
                        onOpenMonth = { isMonthOpen = true },
                    )
                }
            }
        }
    }

    private fun bookingSlotKey(date: LocalDate, time: String) = "$date|$time"
}
