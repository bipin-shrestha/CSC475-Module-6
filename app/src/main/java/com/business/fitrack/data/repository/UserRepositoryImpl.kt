package com.business.fitrack.data.repository

import android.app.Application
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.business.fitrack.data.models.User
import com.business.fitrack.data.models.states.AuthState
import com.business.fitrack.domain.GoogleAuthClient
import com.business.fitrack.domain.UserRepository
import com.business.fitrack.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val googleAuthClient: GoogleAuthClient,
    private val auth: FirebaseAuth,
    private val pref: SharedPreferences
) : UserRepository {

    private val fireStoreUserCollection = Firebase.firestore.collection("users")

    override suspend fun createNewUser(
        userName: String,
        userEmailAddress: String,
        userLoginPassword: String
    ): Resource<AuthResult> {

        return try {

            val registrationResult =
                auth.createUserWithEmailAndPassword(userEmailAddress, userLoginPassword)
                    .await()

            val userId = registrationResult.user?.uid!!
            val newUser = User(
                userName = userName,
                userEmail = userEmailAddress
            )
            fireStoreUserCollection.document(userId).set(newUser).await()

            Resource.Success(registrationResult)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }


    }

    override suspend fun loginUser(email: String, password: String): Resource<AuthResult> {

        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Log.e("login", "logged in user ${result.user?.uid}")
            Resource.Success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }


    }

    override suspend fun getGoogleSignInIntent(): IntentSender? =
        googleAuthClient.googleAuthSignIn()

    override suspend fun loginUserByGoogle(intent: Intent): AuthState =
        googleAuthClient.firebaseSignIn(intent)

    override suspend fun logOutUser() {
        googleAuthClient.signOut()
        //auth.signOut() // GoogleAuthClient.signOut() already does this.
    }

    override fun getCurrentUser(): FirebaseUser? = auth.currentUser
    override fun isFirstTime(): Boolean = pref.getBoolean("firstTime", true)
    override fun noLongerFirstTime() = pref.edit().putBoolean("firstTime", false).apply()
}