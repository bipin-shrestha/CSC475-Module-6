package com.business.fitrack.domain

import android.content.Intent
import android.content.IntentSender
import com.business.fitrack.data.models.states.AuthState
import com.google.firebase.auth.AuthResult
import com.business.fitrack.util.Resource
import com.google.firebase.auth.FirebaseUser

interface UserRepository {

    suspend fun createNewUser(
        userName: String,
        userEmailAddress: String,
        userLoginPassword: String
    ): Resource<AuthResult>

    suspend fun loginUser(email: String, password: String): Resource<AuthResult>

    suspend fun getGoogleSignInIntent(): IntentSender?

    suspend fun loginUserByGoogle(intent: Intent): AuthState

    suspend fun logOutUser()

    fun getCurrentUser(): FirebaseUser?

    fun isFirstTime(): Boolean
    fun noLongerFirstTime()
}