package sk.termin.booking.domain

import java.time.LocalDateTime

enum class AppointmentStatus { Confirmed, Pending }

data class Appointment(
    val id: String,
    val customerName: String,
    val service: String,
    val startsAt: LocalDateTime,
    val durationMinutes: Int,
    val status: AppointmentStatus,
)
