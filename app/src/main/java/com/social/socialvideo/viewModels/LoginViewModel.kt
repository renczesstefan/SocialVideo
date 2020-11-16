package com.social.socialvideo.viewModels;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LoginViewModel : ViewModel() {

    var username = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var _onRegistrationClicked = MutableLiveData<Boolean>()
    val onRegistrationClicked: LiveData<Boolean>
        get() = _onRegistrationClicked
    var _onLoginClicked = MutableLiveData<Boolean>()
    val onLoginClicked: LiveData<Boolean>
        get() = _onLoginClicked


    fun onLogin(){

        _onLoginClicked.value = true
    }

    fun loggedIn(){
        _onLoginClicked.value = false
    }
    
    fun onRegistrationClick(){
        _onRegistrationClicked.value = true
    }

    fun registrationClicked(){
        _onRegistrationClicked.value = false
    }

}
