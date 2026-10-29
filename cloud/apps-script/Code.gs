const BOOKINGS_SHEET = 'Bookings';
const OPENING_HOURS = [
  '08:00', '09:00', '10:00', '11:00', '12:00', '13:00',
  '14:00', '15:00', '16:00', '17:00', '18:00', '19:00',
];

/**
 * GET /exec?action=availability&date=YYYY-MM-DD
 * GET /exec?action=owner-bookings&date=YYYY-MM-DD
 */
function doGet(e) {
  const action = String((e.parameter && e.parameter.action) || '').toLowerCase();
  const date = String((e.parameter && e.parameter.date) || '');

  if (!isValidDate(date)) {
    return json({ ok: false, error: 'A valid date is required.' });
  }

  if (action === 'availability') {
    const bookedTimes = getBookingsForDate(date).map(booking => booking.time);
    return json({
      ok: true,
      date: date,
      availableTimes: OPENING_HOURS.filter(time => !bookedTimes.includes(time)),
      bookedTimes: bookedTimes,
    });
  }

  if (action === 'owner-bookings') {
    return json({ ok: true, date: date, bookings: getBookingsForDate(date) });
  }

  return json({ ok: false, error: 'Unknown action.' });
}

/**
 * POST JSON: { "action": "book", "date": "YYYY-MM-DD", "time": "HH:00", "name": "Customer name" }
 */
function doPost(e) {
  let request;
  try {
    request = JSON.parse((e.postData && e.postData.contents) || '{}');
  } catch (error) {
    return json({ ok: false, error: 'Invalid JSON.' });
  }

  const date = String(request.date || '');
  const time = String(request.time || '');
  const name = normaliseName(request.name);

  if (request.action !== 'book') {
    return json({ ok: false, error: 'Unknown action.' });
  }
  if (!isValidDate(date) || !OPENING_HOURS.includes(time) || name.length < 2) {
    return json({ ok: false, error: 'Invalid booking details.' });
  }

  // The lock prevents two simultaneous requests from reserving the same time.
  const lock = LockService.getScriptLock();
  try {
    lock.waitLock(10000);
    const existing = getBookingsForDate(date);
    if (existing.some(booking => booking.time === time)) {
      return json({ ok: false, status: 'unavailable', error: 'This time is already booked.' });
    }

    getBookingsSheet().appendRow([new Date(), date, time, name, 'booked']);
    return json({ ok: true, status: 'confirmed', date: date, time: time, name: name });
  } catch (error) {
    return json({ ok: false, error: 'The booking could not be saved.' });
  } finally {
    if (lock.hasLock()) {
      lock.releaseLock();
    }
  }
}

function getBookingsForDate(date) {
  const sheet = getBookingsSheet();
  const values = sheet.getDataRange().getValues();
  return values.slice(1)
    .filter(row => String(row[1]) === date && String(row[4]).toLowerCase() === 'booked')
    .map(row => ({
      createdAt: row[0] instanceof Date ? row[0].toISOString() : String(row[0]),
      date: String(row[1]),
      time: String(row[2]),
      name: String(row[3]),
      status: String(row[4]),
    }));
}

function getBookingsSheet() {
  const spreadsheet = SpreadsheetApp.getActiveSpreadsheet();
  let sheet = spreadsheet.getSheetByName(BOOKINGS_SHEET);
  if (!sheet) {
    sheet = spreadsheet.insertSheet(BOOKINGS_SHEET);
    sheet.appendRow(['Created at', 'Date', 'Time', 'Customer name', 'Status']);
    sheet.setFrozenRows(1);
  }
  return sheet;
}

function isValidDate(value) {
  return /^\d{4}-\d{2}-\d{2}$/.test(value);
}

function normaliseName(value) {
  return String(value || '').trim().replace(/\s+/g, ' ').slice(0, 80);
}

function json(payload) {
  return ContentService
    .createTextOutput(JSON.stringify(payload))
    .setMimeType(ContentService.MimeType.JSON);
}
