package sk.termin.booking.data

import sk.termin.booking.domain.Appointment
import sk.termin.booking.domain.AppointmentStatus
import java.time.LocalDateTime

/**
 * Dočasný zdroj dát. Pri pripojení cloudu ho nahradíme implementáciou API
 * bez zásahu do používateľského rozhrania.
 */
interface AppointmentRepository {
    suspend fun getTodayAppointments(): List<Appointment>
}

class DemoAppointmentRepository : AppointmentRepository {
    override suspend fun getTodayAppointments(): List<Appointment> = listOf(
        Appointment("apt-1", "Jana Horváthová", "Konzultácia", LocalDateTime.of(2026, 10, 27, 9, 0), 45, AppointmentStatus.Confirmed),
        Appointment("apt-2", "Michal Kováč", "Úvodné stretnutie", LocalDateTime.of(2026, 10, 27, 10, 30), 30, AppointmentStatus.Confirmed),
        Appointment("apt-3", "Petra Novotná", "Konzultácia", LocalDateTime.of(2026, 10, 27, 13, 0), 60, AppointmentStatus.Pending),
    )
}
