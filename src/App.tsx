import { useEffect, useMemo, useState } from 'react'
import { getAppointmentsForToday } from './services/appointments'
import type { Appointment } from './types/appointment'

const slovakDate = new Intl.DateTimeFormat('sk-SK', {
  weekday: 'long',
  day: 'numeric',
  month: 'long',
})

function formatTime(dateValue: string) {
  return new Intl.DateTimeFormat('sk-SK', { hour: '2-digit', minute: '2-digit' }).format(new Date(dateValue))
}

export default function App() {
  const [appointments, setAppointments] = useState<Appointment[]>([])
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    getAppointmentsForToday().then((items) => {
      setAppointments(items)
      setIsLoading(false)
    })
  }, [])

  const confirmedCount = useMemo(
    () => appointments.filter((appointment) => appointment.status === 'confirmed').length,
    [appointments],
  )

  return (
    <main className="app-shell">
      <header className="topbar">
        <a className="brand" href="/" aria-label="Termín — domovská stránka">
          <span className="brand-mark">T</span>
          <span>termín</span>
        </a>
        <div className="profile" aria-label="Prihlásený používateľ">
          <span className="avatar">AK</span>
          <span className="profile-name">Anna Kováčová</span>
        </div>
      </header>

      <section className="hero">
        <div>
          <p className="eyebrow">PREHĽAD</p>
          <h1>Dnešné termíny</h1>
          <p className="date-label">{slovakDate.format(new Date('2026-10-27T12:00:00'))}</p>
        </div>
        <button className="primary-button" type="button">+ Nový termín</button>
      </section>

      <section className="summary-grid" aria-label="Súhrn termínov">
        <article className="summary-card accent-blue">
          <span>Všetky termíny</span>
          <strong>{isLoading ? '—' : appointments.length}</strong>
          <small>naplánované na dnes</small>
        </article>
        <article className="summary-card accent-green">
          <span>Potvrdené</span>
          <strong>{isLoading ? '—' : confirmedCount}</strong>
          <small>pripravené na stretnutie</small>
        </article>
        <article className="summary-card accent-amber">
          <span>Čakajú na potvrdenie</span>
          <strong>{isLoading ? '—' : appointments.length - confirmedCount}</strong>
          <small>vyžadujú vašu pozornosť</small>
        </article>
      </section>

      <section className="schedule" aria-labelledby="schedule-heading">
        <div className="section-heading">
          <div>
            <p className="eyebrow">HARMONOGRAM</p>
            <h2 id="schedule-heading">Dnešný plán</h2>
          </div>
          <button className="secondary-button" type="button">Zobraziť kalendár</button>
        </div>

        {isLoading ? (
          <p className="empty-state">Načítavam termíny…</p>
        ) : (
          <div className="appointment-list">
            {appointments.map((appointment) => (
              <article className="appointment" key={appointment.id}>
                <time dateTime={appointment.startsAt}>{formatTime(appointment.startsAt)}</time>
                <div className="appointment-main">
                  <h3>{appointment.customerName}</h3>
                  <p>{appointment.service} · {appointment.durationMinutes} min.</p>
                </div>
                <span className={`status status-${appointment.status}`}>
                  {appointment.status === 'confirmed' ? 'Potvrdené' : 'Čaká'}
                </span>
              </article>
            ))}
          </div>
        )}
      </section>
    </main>
  )
}
