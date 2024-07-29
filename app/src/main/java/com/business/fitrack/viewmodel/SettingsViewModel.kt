package com.business.fitrack.viewmodel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.business.fitrack.domain.SettingsRepository
import com.business.fitrack.ui.theme.DynamicThemes
import com.business.fitrack.ui.theme.toDynamicTheme
import com.business.fitrack.ui.theme.toName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository
): ViewModel() {
    var currentTheme: DynamicThemes? by mutableStateOf(DynamicThemes.DEFAULT_LIGHT)
        private set

    init{
        currentTheme = repo.getString("theme").takeIf { it.isNotBlank() }?.toDynamicTheme()
    }

    fun setTheme(
        themeMode: DynamicThemes?
    ) {
        currentTheme = themeMode
        repo.setString("theme", themeMode?.toName() ?: "")
    }
}