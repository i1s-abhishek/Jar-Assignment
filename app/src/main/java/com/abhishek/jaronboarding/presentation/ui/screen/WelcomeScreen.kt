package com.abhishek.jaronboarding.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.abhishek.jaronboarding.R
import com.abhishek.jaronboarding.presentation.ui.theme.CharcoalPurple
import com.abhishek.jaronboarding.presentation.ui.theme.JarOnboardingTheme
import com.abhishek.jaronboarding.presentation.ui.theme.LightGold
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onNext: () -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        delay(500)
        onNext()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CharcoalPurple)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable { onNext() },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcome_message),
                color = colorResource(id = R.color.white),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(R.string.onboarding),
                color = LightGold,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    lineHeight = 32.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    JarOnboardingTheme {
        WelcomeScreen({})
    }
}
