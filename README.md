# Termín — Android

Natívna Android aplikácia na objednávanie termínov, vytvorená v Kotlin/Jetpack Compose.

Momentálne obsahuje funkčný prehľad dňa s ukážkovými termínmi. Doménové modely a úložisko sú samostatné, aby sme ich v ďalšom kroku mohli napojiť na cloudovú databázu, prihlásenie a verejnú rezerváciu.

## Otvorenie a spustenie

1. Otvorte tento priečinok v Android Studio.
2. Vyberte emulátor alebo pripojený Android telefón.
3. Stlačte **Run**.

Projekt vyžaduje JDK 17 a Android SDK Platform 36.

## Štruktúra

- `app/ui` — obrazovky a dizajnový systém
- `app/data` — lokálne zdroje dát, pripravené na výmenu za cloudové API
- `app/domain` — model termínu
