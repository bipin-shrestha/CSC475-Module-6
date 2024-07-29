package com.business.fitrack.data.models.states

import com.business.fitrack.data.models.WorkoutPlan

data class WorkoutPlanState(
    val workoutPlan:WorkoutPlan? = null,
    val loading:Boolean = false,
    val error:String? = null
)
