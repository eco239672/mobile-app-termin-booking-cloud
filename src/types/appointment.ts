export type AppointmentStatus = 'confirmed' | 'pending'

export type Appointment = {
  id: string
  customerName: string
  service: string
  startsAt: string
  durationMinutes: number
  status: AppointmentStatus
}
