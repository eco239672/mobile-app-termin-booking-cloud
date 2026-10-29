package sk.termin.booking.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import sk.termin.booking.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

private val BookingRequestLocale = Locale.forLanguageTag("sk-SK")

@Composable
fun BookingRequestScreen(
    date: LocalDate,
    time: String,
    onBack: () -> Unit,
    onSubmit: () -> Unit,
) {
    var customerName by rememberSaveable { mutableStateOf("") }
    var isSubmitted by rememberSaveable { mutableStateOf(false) }
    val dateTitle = "${date.dayOfMonth}. ${date.month.getDisplayName(TextStyle.FULL, BookingRequestLocale)} ${date.year}"

    Box(modifier = Modifier.fillMaxSize()) {
        androidx.compose.foundation.Image(
            painter = painterResource(R.drawable.parchment_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            TextButton(
                modifier = Modifier.align(Alignment.Start),
                onClick = onBack,
            ) {
                Text("‹ Späť", color = Color.Black, fontFamily = FontFamily.Cursive, fontSize = 28.sp)
            }
            Spacer(Modifier.height(22.dp))
            Text(
                text = dateTitle,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive,
                fontSize = 38.sp,
            )
            Text(
                text = time,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Cursive,
                fontSize = 62.sp,
            )
            Spacer(Modifier.height(34.dp))
            OutlinedTextField(
                value = customerName,
                onValueChange = {
                    customerName = it
                    isSubmitted = false
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Vaše meno") },
                singleLine = true,
            )
            Spacer(Modifier.height(22.dp))
            Button(
                onClick = {
                    onSubmit()
                    isSubmitted = true
                },
                enabled = customerName.trim().isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black.copy(alpha = 0.82f),
                    contentColor = Color.White,
                ),
            ) {
                Text("Odoslať žiadosť", fontFamily = FontFamily.Cursive, fontSize = 24.sp)
            }
            if (isSubmitted) {
                Spacer(Modifier.height(26.dp))
                Text(
                    text = "Termín pre $customerName je označený ako obsadený.",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 24.sp,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Odoslanie barberovi doplníme po pripojení cloudu.",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black.copy(alpha = 0.68f),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 18.sp,
                )
            }
        }
    }
}
