package com.example.firebaseauthwithmvvm.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import kotlin.math.log

class FirebaseSource {

    private val TAG = FirebaseSource::class.java.canonicalName

    private val firebaseAuth: FirebaseAuth by lazy {
        Log.e(TAG, "FirebaseSoure " + FirebaseAuth.getInstance())

        FirebaseAuth.getInstance()
    }


    fun login(email: String, pass: String) = Completable.create { emitter ->
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (!emitter.isDisposed) {
                if (it.isSuccessful)
                    emitter.onComplete()
                else
                    emitter.onError(it.exception!!)
            } else {
                Log.e(TAG, "FirebaseSoure Register2222 ")
            }
        }
    }


    fun register(email: String, pass: String) = Completable.create { emitter ->
        Log.e(TAG, "FirebaseSoure Register999 " + email + " " + pass)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.e(TAG, "FirebaseSoure Register Success " + emitter.isDisposed + " c " + it.exception)
                } else {
                    Log.e(TAG, "FirebaseSoure Register Not Success ")
                }
                if (emitter.isDisposed) {
                    Log.e(TAG, "FirebaseSoure Register1111 ")
                    if (it.isSuccessful) {
                        Log.e(TAG, "FirebaseSoure Register Successful ")
                        emitter.onComplete()
                    } else {
                        Log.e(TAG, "FirebaseSoure Register Failed ")
                        emitter.onError(it.exception!!)
                    }
                } else {
                    Log.e(TAG, "FirebaseSoure Register error 2 ")

                }
            }.addOnFailureListener {
                Log.e(TAG, "FirebaseSoure Register Failed3 ")

            }

    }

    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

}