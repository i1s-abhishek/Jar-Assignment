package com.abhishek.jaronboarding.presentation.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.with
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.abhishek.jaronboarding.R
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun OnboardingFeatureCard(
    modifier: Modifier = Modifier,
    educationData: OnBoardingResponse.Data.ManualBuyEducationData.EducationCardList,
    onExpandChange: (Int) -> Unit,
    index: Int,
    expandedIndex: Int,
    backgroundBrush: Brush,
    isVisible: Boolean
) {
    val expanded = index == expandedIndex

    val stroke = Brush.verticalGradient(
        colors = listOf(
            Color(educationData.strokeStartColor.toColorInt()).copy(alpha = 0.2f),
            Color(educationData.strokeEndColor.toColorInt())
        )
    )
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { height -> height + 1000 },
            animationSpec = tween(
                durationMillis = 500,
                easing = EaseInOutCubic
            )
        ) + fadeIn(
            animationSpec = tween(500)
        )
    ) {
        SharedTransitionLayout {
            AnimatedContent(
                expanded,
                label = "OnboardingFeatureCard",
                transitionSpec = {
                    expandIn(
                        animationSpec = tween(300, easing = EaseInOutCubic),
                        expandFrom = Alignment.Center,
                        initialSize = { IntSize(it.width, it.height) }
                    ) + fadeIn(
                        animationSpec = tween(300)
                    ) with shrinkOut(
                        animationSpec = tween(300, easing = EaseInOutCubic),
                        shrinkTowards = Alignment.Center,
                        targetSize = { IntSize(it.width, it.height) }
                    ) + fadeOut(
                        animationSpec = tween(300)
                    )
                }
            ) { targetState ->
                if (targetState) {
                    ExpendedCard(
                        modifier,
                        stroke,
                        backgroundBrush,
                        educationData,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )

                } else {
                    UnExpendedCard(
                        modifier,
                        backgroundBrush,
                        onExpandChange,
                        index,
                        educationData,
                        animatedVisibilityScope = this@AnimatedContent,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun UnExpendedCard(
    modifier: Modifier,
    background: Brush,
    onExpandChange: (Int) -> Unit,
    index: Int,
    educationData: OnBoardingResponse.Data.ManualBuyEducationData.EducationCardList,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val rotation = animateFloatAsState(
        targetValue = 6f,
        animationSpec = tween(durationMillis = 200),
        label = "TiltAnimation"
    )

    Row(
        modifier = modifier
            .graphicsLayer {
                rotation.value
            }
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(background)
            .padding(horizontal = 12.dp)
            .clickable {
                onExpandChange(index)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                model = educationData.image,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .sharedElement(
                        rememberSharedContentState(key = "image"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )

            Text(
                text = educationData.collapsedStateText ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp,
                color = Color.White,
                modifier = Modifier.sharedElement(
                    rememberSharedContentState(key = "text"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )

            Image(
                painterResource(R.drawable.ic_arrow_down), contentDescription = "Arrow Icon"
            )
        }
    }
}

@Composable
@OptIn(ExperimentalSharedTransitionApi::class)
private fun ExpendedCard(
    modifier: Modifier,
    stroke: Brush,
    background: Brush,
    educationData: OnBoardingResponse.Data.ManualBuyEducationData.EducationCardList,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .border(1.dp, stroke, shape = RoundedCornerShape(28.dp))
            .background(background)
            .padding(16.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                model = educationData.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .sharedElement(
                        rememberSharedContentState(key = "image"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )

            Text(
                text = educationData.expandStateText ?: "",
                fontSize = 20.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.sharedElement(
                    rememberSharedContentState(key = "text"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun ShowOnboardingFeatureCard() {
    OnboardingFeatureCard(
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
        index = 1,
        expandedIndex = 2,
        backgroundBrush = Brush.verticalGradient(
            colors = listOf(Color(0xFF272239), Color(0xFF272239))
        ),
        isVisible = true
    )
}
