package com.abhishek.jaronboarding.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.jaronboarding.R
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse
import com.abhishek.jaronboarding.presentation.stateHolders.OnBoardingUiState
import com.abhishek.jaronboarding.presentation.stateHolders.OnBoardingViewModel
import com.abhishek.jaronboarding.presentation.ui.components.GoldActionButton
import com.abhishek.jaronboarding.presentation.ui.components.OnboardingFeatureCard
import com.abhishek.jaronboarding.presentation.ui.components.TopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    onBackClick: () -> Unit,
    onSaveGoldClick: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val visibleCards by viewModel.visibleCards.collectAsState()
    val expandedIndex by viewModel.expandedIndex.collectAsState()

    val successState = uiState as? OnBoardingUiState.Success
    val data = successState?.data?.data
    val saveButtonCta = data?.manualBuyEducationData?.saveButtonCta
    val lastIndex = data?.manualBuyEducationData?.educationCardList?.lastIndex ?: -1

    val screenBackgroundBrush = getBackgroundBrush(data, expandedIndex)

    val carBackgroundBrush = getCardBackgroundBrush(data, expandedIndex)

    val isSaveClicked = remember { mutableStateOf(false) }
    val slideOffset by animateFloatAsState(
        targetValue = if (isSaveClicked.value) 10000f else 0f,
        animationSpec = tween(500, easing = EaseInOutCubic),
        label = "SlideOutAnimation"
    )

    Scaffold(topBar = {
        TopBar(
            modifier = Modifier
                .background(Color.Transparent)
                .statusBarsPadding()
                .graphicsLayer(translationY = -slideOffset),
            title = stringResource(R.string.onboarding),
            onBackPressed = onBackClick
        )
    }, bottomBar = {
        AnimatedVisibility(
            visible = expandedIndex == lastIndex,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()) {
            saveButtonCta?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GoldActionButton(
                        modifier = Modifier.clickable {
                            isSaveClicked.value = true
                            viewModel.viewModelScope.launch {
                                delay(200)
                                onSaveGoldClick()
                            }
                        },
                        saveButtonCta = it,
                        ctaLottie = data.manualBuyEducationData?.ctaLottie.orEmpty()
                    )
                }
            }
        }
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .graphicsLayer(translationY = -slideOffset)
                .fillMaxSize()
                .background(screenBackgroundBrush)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            BottomRadialGradient()
            when (uiState) {
                is OnBoardingUiState.Success -> {
                    OnBoardingContent(
                        onBoardingData = data,
                        onExpandedCardChange = viewModel::onExpandCard,
                        expandedIndex = expandedIndex,
                        backgroundBrush = carBackgroundBrush,
                        visibleCards = visibleCards
                    )
                }

                is OnBoardingUiState.Loading -> CircularProgressIndicator()
                is OnBoardingUiState.Error -> Text(
                    text = (uiState as OnBoardingUiState.Error).message, color = Color.White
                )
            }
        }
    }
}

@Composable
private fun OnBoardingContent(
    onBoardingData: OnBoardingResponse.Data?,
    onExpandedCardChange: (Int) -> Unit,
    expandedIndex: Int,
    backgroundBrush: Brush,
    visibleCards: Set<Int>
) {
    val cards = onBoardingData?.manualBuyEducationData?.educationCardList ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cards.forEachIndexed { index, card ->
            OnboardingFeatureCard(
                educationData = card,
                index = index,
                isVisible = index in visibleCards,
                expandedIndex = expandedIndex,
                onExpandChange = { isExpanded ->
                    onExpandedCardChange(isExpanded)
                },
                backgroundBrush = backgroundBrush
            )
        }
    }
}

@Composable
private fun BottomRadialGradient() {
    val brush = Brush.radialGradient(
        colorStops = arrayOf(
            0.0f to colorResource(R.color.light_yellow).copy(alpha = 0.6f),
            0.5f to colorResource(R.color.light_yellow).copy(alpha = 0.3f),
            1.0f to colorResource(R.color.light_dark_yellow).copy(alpha = 0.0f)
        ),
    )
    Box(
        modifier = Modifier
            .size(453.dp)
            .offset(y = 500.dp)
            .background(brush, shape = CircleShape)
    )
}

private fun getBackgroundBrush(data: OnBoardingResponse.Data?, index: Int): Brush {
    val card = data?.manualBuyEducationData?.educationCardList?.getOrNull(index)
    val start = card?.backGroundColor ?: "#713A65"
    return Brush.verticalGradient(
        colors = listOf(
            Color(start.toColorInt()),
            Color(start.toColorInt())
        )
    )
}


private fun getCardBackgroundBrush(data: OnBoardingResponse.Data?, index: Int): Brush {
    val card = data?.manualBuyEducationData?.educationCardList?.getOrNull(index)
    val start = card?.startGradient ?: "#713A65"
    val end = card?.endGradient ?: "#713A65"
    return Brush.verticalGradient(
        colors = listOf(
            Color(start.toColorInt()),
            Color(end.toColorInt())
        )
    )
}

@Preview
@Composable
private fun PreviewOnBoardingScreen() {
    OnBoardingScreen(onBackClick = {}, onSaveGoldClick = {})
}
