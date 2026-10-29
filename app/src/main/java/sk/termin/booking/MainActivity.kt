package sk.termin.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import sk.termin.booking.ui.BookingRequestScreen
import sk.termin.booking.ui.MonthDaysScreen
import sk.termin.booking.ui.OwnerScheduleScreen
import sk.termin.booking.ui.TimeSlotsScreen
import sk.termin.booking.ui.theme.TerminTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    private var selectedDate by mutableStateOf(LocalDate.now())
    private var isMonthOpen by mutableStateOf(false)
    private var selectedBookingDate by mutableStateOf<LocalDate?>(null)
    private var selectedBookingTime by mutableStateOf<String?>(null)
    private var bookedSlots by mutableStateOf<Set<String>>(emptySet())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        selectedDate = selectedDate,
                        onDateSelected = { selectedDate = it },
                        onOpenMonth = { isMonthOpen = true },
                    )
                }
            }
        }
    }

    private fun bookingSlotKey(date: LocalDate, time: String) = "$date|$time"
}
