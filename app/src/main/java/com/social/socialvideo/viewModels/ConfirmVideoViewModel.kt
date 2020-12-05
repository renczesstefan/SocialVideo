package com.social.socialvideo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfirmVideoViewModel: ViewModel() {
    var _confirmed = MutableLiveData<Boolean>()
    val confirmed: LiveData<Boolean>
        get() = _confirmed

    fun confirmVideo() {
        _confirmed.value = true
    }
}