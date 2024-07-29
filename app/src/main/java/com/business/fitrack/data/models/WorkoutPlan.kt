package com.business.fitrack.data.models

import com.business.fitrack.util.DifficultyLevels
import java.time.DayOfWeek

data class WorkoutPlan (
    val name:String? = null,
    val workouts:ArrayList<DayOfWeek>? = null,
    val difficulty: DifficultyLevels.Difficulty?  = null,
    val duration:Int? = null,
)
