package com.abhishek.jaronboarding.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.abhishek.jaronboarding.R
import com.abhishek.jaronboarding.presentation.ui.components.TopBar
import com.abhishek.jaronboarding.presentation.ui.theme.DarkIndigo

@Composable
fun LandingScreen(
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBar(
                modifier = Modifier.background(DarkIndigo),
                title = stringResource(R.string.landing_page_title),
                onBackPressed = onBackPressed
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(DarkIndigo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.landing_page_title),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}


@Composable
@Preview
fun ShowLandingScreen() {
    LandingScreen()
}