package com.business.fitrack.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.business.fitrack.ui.composables.login.LoginScreen
import com.business.fitrack.ui.composables.onboarding.OnboardingScreen
import com.business.fitrack.ui.composables.signup.SignUpScreen
import com.business.fitrack.viewmodel.UserViewModel

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    userViewModel: UserViewModel,
    scaffoldState: ScaffoldState
) {
    navigation(
        startDestination =
            if (userViewModel.isFirstTime()) Screens.Onboarding.route
            else Screens.Login.route,
        route = LOGIN_ROUTE
    ) {
        composable(
            route = Screens.Onboarding.route
        ) {
            OnboardingScreen(navController, userViewModel::noLongerFirstTime)
            bottomBarState.value = false
        }

        composable(route = Screens.Login.route) {
            LoginScreen(navController, userViewModel, scaffoldState)
            bottomBarState.value = false
        }

        composable(route = Screens.Signup.route) {
            SignUpScreen(navController, userViewModel, scaffoldState)
            bottomBarState.value = false
        }
    }
}