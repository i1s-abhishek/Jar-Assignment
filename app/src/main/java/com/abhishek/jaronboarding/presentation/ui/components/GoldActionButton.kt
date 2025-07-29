package com.abhishek.jaronboarding.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun GoldActionButton(
    saveButtonCta: OnBoardingResponse.Data.ManualBuyEducationData.SaveButtonCta,
    ctaLottie: String,
    modifier: Modifier = Modifier
) {
    val backgroundColor = saveButtonCta.backgroundColor.parseColorOrDefault("#272239")
    val borderColor = saveButtonCta.strokeColor.parseColorOrDefault("#272239")
    val textColor = saveButtonCta.textColor.parseColorOrDefault("#FDF3D6")

    Row(
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(31.dp))
            .background(backgroundColor, shape = RoundedCornerShape(31.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = saveButtonCta.text.orEmpty(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.width(8.dp))

        LottieFromUrl(url = ctaLottie)
    }
}

@Composable
fun LottieFromUrl(url: String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(url))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f
    )

    Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
        if (composition != null) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(24.dp)
                    .rotate(180f)
            )
        } else {
            CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(16.dp))
        }
    }
}

fun String?.parseColorOrDefault(default: String): Color = try {
    Color((this ?: default).toColorInt())
} catch (e: Exception) {
    Color(default.toColorInt())
}

@Preview
@Composable
fun ShowGoldActionButtonPreview() {
    GoldActionButton(
        ctaLottie = "https://assets2.lottiefiles.com/packages/lf20_u4yrau.json",
        saveButtonCta = OnBoardingResponse.Data.ManualBuyEducationData.SaveButtonCta(
            text = "Save in Gold",
            textColor = "#FDF3D6",
            backgroundColor = "#272239",
            strokeColor = "#272239",
            icon = null,
            order = null,
            deeplink = null
        )
    )
}
