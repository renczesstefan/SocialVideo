package com.social.socialvideo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    var _onLogout = MutableLiveData<Boolean>()
    val onLogout: LiveData<Boolean>
        get() = _onLogout
    var _onPasswordChange = MutableLiveData<Boolean>()
    val onPasswordChange: LiveData<Boolean>
        get() = _onPasswordChange


    fun onLogout() {
        _onLogout.value = true
    }

    fun onPasswordChange() {
        _onPasswordChange.value = true
    }

    fun resetOnLogout() {
        _onLogout.value = false
    }

    fun resetOnPasswordChange() {
        _onPasswordChange.value = false
    }
}