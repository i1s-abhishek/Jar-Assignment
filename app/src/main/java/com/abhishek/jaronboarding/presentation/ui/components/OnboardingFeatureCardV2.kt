package com.abhishek.jaronboarding.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.abhishek.jaronboarding.R
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse

@Composable
fun OnboardingFeatureCardV2(
    modifier: Modifier = Modifier,
    educationData: OnBoardingResponse.Data.ManualBuyEducationData.EducationCardList,
    onExpandChange: (Int) -> Unit,
    index: Int,
    expandedIndex: Int,
) {
    var expanded = index == expandedIndex

    val stroke = Brush.linearGradient(
        colors = listOf(
            Color(educationData.strokeStartColor.toColorInt()),
            Color(educationData.strokeEndColor.toColorInt())
        )
    )

    val backgroundColor =
        Color(educationData.backGroundColor.toColorInt())

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically(),
        modifier = Modifier.animateContentSize()
    ) {
        if (expanded) {
            Column(
                modifier = modifier
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, stroke, shape = RoundedCornerShape(16.dp))
                    .background(
                        backgroundColor
                    )
                    .clickable {
                        onExpandChange(-1)
                        expanded = !expanded
                    }
                    .padding(16.dp)
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                AsyncImage(
                    model = educationData.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )

                Text(
                    text = educationData.expandStateText ?: "",
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

        } else {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, stroke, shape = RoundedCornerShape(16.dp))
                    .background(
                        backgroundColor
                    )
                    .padding(
                        horizontal = 12.dp
                    )
                    .clickable {
                        onExpandChange(index)
                        expanded = !expanded
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = educationData.image,
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = educationData.collapsedStateText ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    color = Color.White,
                )
                Image(
                    painterResource(R.drawable.ic_arrow_down),
                    contentDescription = "Arrow Icon"
                )
            }
        }
    }

}


@Composable
@Preview(showBackground = true)
fun ShowOnboardingFeatureCardV2() {
    OnboardingFeatureCardV2(
        educationData = OnBoardingResponse.Data.ManualBuyEducationData.EducationCardList(
            image = "https://img.myjar.app/yXXRNLKGxWkoWRyyzurnZr3UJNHbqVaCHiLk1VYGlDM/rs:fit:0:0/q:60/format:webp/plain/https://cdn.myjar.app/Homefeed/engagement_card/buyGoldEducation2.webp",
            endGradient = "#286642",
            startGradient = "#286642",
            expandStateText = "Buy gold anytime, anywhere",
            collapsedStateText = "Buy gold anytime, anywhere",
            strokeEndColor = "",
            backGroundColor = "",
            strokeStartColor = ""
        ),
        onExpandChange = {},
        index = 1, expandedIndex = 2
    )
}
