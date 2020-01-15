package com.example.firebaseauthwithmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.firebaseauthwithmvvm.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    //email nad password for the input
    var email: String? = null
    var pass: String? = null

    //auth listener
    var authListener: AuthListener? = null


    private val disposables = CompositeDisposable()


    val user by lazy {
        repository.currentUser()
    }

    //function to perform login
    fun login() {

        if (email.isNullOrEmpty() || pass.isNullOrEmpty()) {
            authListener?.onFailure("Invaild Phone or Password")
            return
        }

        authListener?.onStarted()


        val disposable = repository.login(email!!, pass!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()

            }, {
                authListener?.onFailure(it.message!!)
            })

        disposables.add(disposable)
    }


    fun signUp() {

        if (email.isNullOrEmpty() || pass.isNullOrEmpty()) {

            authListener?.onFailure("Please Input all values")

            return
        }

        authListener?.onStarted()

        val disposable = repository.register(email!!, pass!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })

        disposables.add(disposable)
    }


    fun goToSignUp(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}