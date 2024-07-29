package com.business.fitrack

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.business.fitrack.data.service.WorkoutTimerService
import com.business.fitrack.ui.navigation.RootNavGraph
import com.business.fitrack.ui.theme.DynamicThemes
import com.business.fitrack.ui.theme.fitrackDynamicTheme
import com.business.fitrack.ui.theme.InFitTheme
import com.business.fitrack.util.getTimeStringFromDouble
import com.business.fitrack.viewmodel.SettingsViewModel
import com.business.fitrack.viewmodel.UserViewModel
import com.business.fitrack.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController:NavHostController

    private val userViewModel: UserViewModel by viewModels()
    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private var timeElapsed = 0.0
    private lateinit var serviceIntent: Intent


    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            timeElapsed = intent.getDoubleExtra(WorkoutTimerService.TIME_ELAPSED, 0.0)
            workoutViewModel.timerText = getTimeStringFromDouble(timeElapsed)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceIntent = Intent(this, WorkoutTimerService::class.java)
        registerReceiver(
            updateTime,
            IntentFilter(WorkoutTimerService.TIMER_UPDATED), RECEIVER_EXPORTED
        )
        setContent {
            fitrackDynamicTheme( themeMode = decideTheme(settingsViewModel.currentTheme) ) {
                navController = rememberNavController()
                RootNavGraph(
                    navController,
                    userViewModel,
                    workoutViewModel,
                    settingsViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(updateTime)
        stopService(serviceIntent)
    }

    @Composable
    private fun decideTheme(theme: DynamicThemes?) = theme ?: (
        if (isSystemInDarkTheme()) DynamicThemes.DEFAULT_DARK
        else DynamicThemes.DEFAULT_LIGHT
    )
}


