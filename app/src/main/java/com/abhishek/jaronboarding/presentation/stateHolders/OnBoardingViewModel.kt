package com.abhishek.jaronboarding.presentation.stateHolders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.jaronboarding.domain.OnBoardingRepository
import com.abhishek.jaronboarding.domain.common.Resource
import com.abhishek.jaronboarding.domain.dto.respose.OnBoardingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    val onBoardingRepository: OnBoardingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<OnBoardingUiState>(OnBoardingUiState.Loading)
    val uiState: StateFlow<OnBoardingUiState> = _uiState

    private val _expandedIndex = MutableStateFlow(-1)
    val expandedIndex: StateFlow<Int> = _expandedIndex

    private lateinit var cardAnimationConfig: CardAnimationConfig

    init {
        fetchOnBoardingData()
    }

    private fun fetchOnBoardingData() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = onBoardingRepository.getOnBoardingData()) {
                is Resource.Success -> {
                    _uiState.value = OnBoardingUiState.Success(result.data)
                    updateAnimationConfig(result.data)
                    startCardSequence(
                        result.data.data?.manualBuyEducationData?.educationCardList?.size ?: 0
                    )
                }

                is Resource.Error -> {
                    _uiState.value = OnBoardingUiState.Error(
                        result.exception.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }


    private fun updateAnimationConfig(data: OnBoardingResponse) {
        data.data?.manualBuyEducationData?.let { educationData ->
            cardAnimationConfig = CardAnimationConfig(
                expandStayInterval = educationData.expandCardStayInterval ?: 3000L,
                tiltInterval = educationData.collapseCardTiltInterval ?: 1000L,
                introInterval = educationData.collapseExpandIntroInterval ?: 500L,
                bottomToCenterTransition = educationData.bottomToCenterTranslationInterval
                    ?: 1500L
            )
        }
    }

    fun startCardSequence(totalCards: Int) {
        viewModelScope.launch {
            delay(cardAnimationConfig.introInterval)
            for (i in 0 until totalCards) {
                _expandedIndex.value = i
                delay(cardAnimationConfig.expandStayInterval)
            }
        }
    }

    fun onExpandCard(index: Int) {
        _expandedIndex.value = index
    }

}

sealed class OnBoardingUiState {
    data object Loading : OnBoardingUiState()
    data class Success(val data: OnBoardingResponse) : OnBoardingUiState()
    data class Error(val message: String) : OnBoardingUiState()
}

data class CardAnimationConfig(
    val expandStayInterval: Long,
    val tiltInterval: Long,
    val introInterval: Long,
    val bottomToCenterTransition: Long
)