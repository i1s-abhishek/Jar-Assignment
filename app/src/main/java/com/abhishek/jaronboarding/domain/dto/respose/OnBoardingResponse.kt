package com.abhishek.jaronboarding.domain.dto.respose

import kotlinx.serialization.SerialName

data class OnBoardingResponse(
    @SerialName("data") var data: Data? = Data(),
    @SerialName("success") var success: Boolean? = null
) {

    data class Data(
        @SerialName("manualBuyEducationData") var manualBuyEducationData: ManualBuyEducationData? = ManualBuyEducationData()
    ) {
        data class ManualBuyEducationData(
            @SerialName("cohort") var cohort: String? = null,
            @SerialName("ctaLottie") var ctaLottie: String? = null,
            @SerialName("seenCount") var seenCount: String? = null,
            @SerialName("actionText") var actionText: String? = null,
            @SerialName("introTitle") var introTitle: String? = null,
            @SerialName("screenType") var screenType: String? = null,
            @SerialName("combination") var combination: String? = null,
            @SerialName("toolBarIcon") var toolBarIcon: String? = null,
            @SerialName("toolBarText") var toolBarText: String? = null,
            @SerialName("introSubtitle") var introSubtitle: String? = null,
            @SerialName("saveButtonCta") var saveButtonCta: SaveButtonCta = SaveButtonCta(),
            @SerialName("educationCardList") var educationCardList: ArrayList<EducationCardList> = arrayListOf(),
            @SerialName("introSubtitleIcon") var introSubtitleIcon: String? = null,
            @SerialName("expandCardStayInterval") var expandCardStayInterval: Long? = null,
            @SerialName("shouldShowOnLandingPage") var shouldShowOnLandingPage: Boolean? = null,
            @SerialName("collapseCardTiltInterval") var collapseCardTiltInterval: Long? = null,
            @SerialName("shouldShowBeforeNavigating") var shouldShowBeforeNavigating: Boolean? = null,
            @SerialName("collapseExpandIntroInterval") var collapseExpandIntroInterval: Long? = null,
            @SerialName("bottomToCenterTranslationInterval") var bottomToCenterTranslationInterval: Long? = null

        ) {
            data class SaveButtonCta(
                @SerialName("icon") var icon: String? = null,
                @SerialName("text") var text: String? = null,
                @SerialName("order") var order: String? = null,
                @SerialName("deeplink") var deeplink: String? = null,
                @SerialName("textColor") var textColor: String? = null,
                @SerialName("strokeColor") var strokeColor: String? = null,
                @SerialName("backgroundColor") var backgroundColor: String? = null
            )

            data class EducationCardList(
                @SerialName("image") var image: String? = null,
                @SerialName("endGradient") var endGradient: String,
                @SerialName("startGradient") var startGradient: String,
                @SerialName("strokeEndColor") var strokeEndColor: String,
                @SerialName("backGroundColor") var backGroundColor: String,
                @SerialName("expandStateText") var expandStateText: String? = null,
                @SerialName("strokeStartColor") var strokeStartColor: String,
                @SerialName("collapsedStateText") var collapsedStateText: String? = null
            )

        }
    }
}