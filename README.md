# OnboardingFeatureCard Component

A customizable animated card component for onboarding flows in Android Jetpack Compose applications.

## Features

- Smooth animations for expanding/collapsing states
- Shared element transitions between states
- Tilt animations during state changes
- Customizable background gradients and stroke colors
- Support for async image loading
- Configurable text content and styling

## Usage

```kotlin
OnboardingFeatureCard(
    modifier = Modifier,
    educationData = educationCardData,
    onExpandChange = { index -> /* Handle expansion */ },
    index = cardIndex,
    expandedIndex = currentExpandedIndex,
    backgroundBrush = cardBackgroundBrush,
    isVisible = shouldShowCard
)
