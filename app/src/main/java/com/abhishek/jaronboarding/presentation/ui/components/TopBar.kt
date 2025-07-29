package com.abhishek.jaronboarding.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abhishek.jaronboarding.R

@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = modifier,
            onClick = onBackPressed
        ) {
            Image(
                painter = painterResource(R.drawable.ic_arrow),
                contentDescription = "Back"
            )
        }
        Spacer(
            modifier = Modifier
                .width(8.dp)
        )
        Text(
            color = colorResource(R.color.white),
            text = title,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun ShowTopBar() {
    TopBar(title = "Onboarding") {}
}