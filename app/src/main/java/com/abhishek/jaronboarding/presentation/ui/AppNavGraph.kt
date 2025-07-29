package com.abhishek.jaronboarding.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhishek.jaronboarding.presentation.ui.screen.LandingScreen
import com.abhishek.jaronboarding.presentation.ui.screen.OnBoardingScreen
import com.abhishek.jaronboarding.presentation.ui.screen.WelcomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Welcome.route
    ) {
        composable(NavRoutes.Welcome.route) {
            WelcomeScreen(
                onNext = {
                    navController.navigate(NavRoutes.Onboarding.route)
                }
            )
        }
        composable(NavRoutes.Onboarding.route) {
            OnBoardingScreen(
                onSaveGoldClick = {
                    navController.navigate(NavRoutes.Landing.route)
                },
                onBackClick = {
                    navController.navigate(NavRoutes.Welcome.route)
                }
            )
        }
        composable(NavRoutes.Landing.route) {
            LandingScreen(
                onBackPressed = {
                    navController.navigate(NavRoutes.Welcome.route)
                }
            )
        }
    }
}

sealed class NavRoutes(val route: String) {
    object Welcome : NavRoutes("welcome")
    object Onboarding : NavRoutes("onboarding")
    object Landing : NavRoutes("landing")
}
