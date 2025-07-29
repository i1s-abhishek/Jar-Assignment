package com.abhishek.jaronboarding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.abhishek.jaronboarding.presentation.stateHolders.OnBoardingViewModel
import com.abhishek.jaronboarding.presentation.ui.AppNavGraph
import com.abhishek.jaronboarding.presentation.ui.theme.JarOnboardingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JarOnboardingTheme {
                val navController = rememberNavController()
                val viewModel: OnBoardingViewModel = hiltViewModel()

                AppNavGraph(navController = navController)
            }
        }
    }
}
