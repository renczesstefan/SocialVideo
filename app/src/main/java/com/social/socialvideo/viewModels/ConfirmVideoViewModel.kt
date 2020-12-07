package com.social.socialvideo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConfirmVideoViewModel: ViewModel() {
    var _confirmed = MutableLiveData<Boolean>()
    val confirmed: LiveData<Boolean>
        get() = _confirmed
    var _discarded = MutableLiveData<Boolean>()
    val discarded: LiveData<Boolean>
        get() = _discarded

    fun confirmVideo() {
        _confirmed.value = true
    }

    fun discardVideo() {
        _discarded.value = true
    }
}