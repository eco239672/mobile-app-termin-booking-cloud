import type { Appointment } from '../types/appointment'

// Dočasná lokálna implementácia. Neskôr ju nahradíme volaním cloudového API.
const demoAppointments: Appointment[] = [
  { id: 'apt-1', customerName: 'Jana Horváthová', service: 'Konzultácia', startsAt: '2026-10-27T09:00:00', durationMinutes: 45, status: 'confirmed' },
  { id: 'apt-2', customerName: 'Michal Kováč', service: 'Úvodné stretnutie', startsAt: '2026-10-27T10:30:00', durationMinutes: 30, status: 'confirmed' },
  { id: 'apt-3', customerName: 'Petra Novotná', service: 'Konzultácia', startsAt: '2026-10-27T13:00:00', durationMinutes: 60, status: 'pending' },
]

export async function getAppointmentsForToday(): Promise<Appointment[]> {
  return Promise.resolve(demoAppointments)
}
