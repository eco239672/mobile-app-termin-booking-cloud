# Simple cloud booking setup

This folder contains the lightweight cloud service for B-Shop. It uses one Google Sheet and a Google Apps Script web app instead of Firebase.

## What it does

- `GET ?action=availability&date=YYYY-MM-DD` returns free and booked times for the client app.
- `GET ?action=owner-bookings&date=YYYY-MM-DD` returns a barber's bookings for a day.
- `POST` with a booking request checks the time again and saves it only when it remains free.

The script uses a script lock, so two requests arriving at the same moment cannot both reserve the same hour.

## Deploy it

1. Create a new Google Sheet named `B-Shop bookings` in the barber's Google account.
2. Open **Extensions → Apps Script** in that sheet.
3. Replace the default code with [`apps-script/Code.gs`](apps-script/Code.gs).
4. Click **Deploy → New deployment → Web app**.
5. Set **Execute as** to the Google account that owns the sheet.
6. For this prototype, set access so the two apps can use the URL without a Google sign-in.
7. Copy the deployment URL ending in `/exec`.

The next Android step is to place that URL in the app configuration and replace the current local booking storage with requests to this service.

## Data stored

The `Bookings` sheet stores only the creation time, date, time, customer name, and status. Do not place phone numbers, payment details, or other sensitive data in this lightweight prototype.
