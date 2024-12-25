package com.moksh.data.datasource

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.moksh.data.entities.local.UserEntity
import com.moksh.data.entities.utils.safeCall
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userDataSource: UserDataSource,
    private val activity: Activity
) {
    suspend fun sendOtp(phoneNumber: String): Result<String, DataError> {
        return safeCall {
            suspendCoroutine { continuation ->
                val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                    .setPhoneNumber(phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(activity)
                    .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            continuation.resume("Verification completed")
                        }

                        override fun onVerificationFailed(exception: FirebaseException) {
                            continuation.resumeWithException(exception)
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            continuation.resume(verificationId)
                        }
                    })
                    .build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }
    }

    suspend fun verifyOtp(
        phoneNumber: String,
        verificationId: String,
        code: String
    ): Result<AuthResult, DataError> {
        return safeCall {
            suspendCoroutine { continuation ->
                val credential = PhoneAuthProvider.getCredential(verificationId, code)
                firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        runBlocking {
                            try {
                                authResult.user?.uid?.let {
                                    val entity = UserEntity(
                                        id = it,
                                        phoneNumber = phoneNumber,
                                    )
                                    userDataSource.saveUser(entity)
                                    continuation.resume(authResult)
                                }
                                    ?: continuation.resumeWithException(Exception("User ID not found"))
                            } catch (e: Exception) {
                                continuation.resumeWithException(e)
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
        }
    }
}