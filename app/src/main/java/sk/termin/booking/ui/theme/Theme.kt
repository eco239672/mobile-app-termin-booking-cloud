package sk.termin.booking.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val TerminColors = lightColorScheme(
    primary = Color(0xFF0F7184),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6EEF1),
    onPrimaryContainer = Color(0xFF073642),
    secondary = Color(0xFF4F9C70),
    background = Color(0xFFF5F7F8),
    surface = Color.White,
    onSurface = Color(0xFF12212A),
)

@Composable
fun TerminTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TerminColors,
        typography = TerminTypography,
        content = content,
    )
}
