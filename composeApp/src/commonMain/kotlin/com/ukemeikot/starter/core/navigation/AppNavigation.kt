package com.ukemeikot.starter.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.ukemeikot.starter.features.auth.presentation.AuthScreen
import com.ukemeikot.starter.features.home.presentation.HomeScreen
import com.ukemeikot.starter.features.onboarding.presentation.OnboardingScreen

@Composable
fun AppNavigation(startDestination: AppDestination) {
    val backStack = remember { mutableStateListOf(startDestination) }

    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AppDestination.Onboarding> {
                OnboardingScreen(
                    onComplete = {
                        backStack.clear()
                        backStack.add(AppDestination.Auth())
                    },
                )
            }
            entry<AppDestination.Auth> {
                AuthScreen(
                    initialMode = it.initialMode,
                    onAuthSuccess = {
                        backStack.clear()
                        backStack.add(AppDestination.Home)
                    },
                )
            }
            entry<AppDestination.Home> {
                HomeScreen(
                    onLogout = {
                        backStack.clear()
                        backStack.add(AppDestination.Auth())
                    },
                )
            }
        },
    )
}
