package com.social.socialvideo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.social.socialvideo.entities.RegistrationRequest
import com.social.socialvideo.entities.RegistrationResponse
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.retrofit
import com.social.socialvideo.utils.PasswordUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : ViewModel() {

    var userId = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var passwordConfirmation = MutableLiveData<String>()
    var _onUserRegistration = MutableLiveData<ServerResponse>()
    val onUserRegistration: LiveData<ServerResponse>
        get() = _onUserRegistration

    fun registerUser() {
        if (PasswordUtil.checkMatchingPasswords(password.value, passwordConfirmation.value)) {

            val registrationRequest = initRegistrationRequest()
            val apiService = retrofit.create(RestApiService::class.java)
            val regResponse: Call<RegistrationResponse> = apiService.createUser(registrationRequest)

            regResponse.enqueue(object : Callback<RegistrationResponse> {
                override fun onFailure(call: Call<RegistrationResponse>?, t: Throwable?) {
                    _onUserRegistration.value = ServerResponse.SERVER_ERROR
                }

                override fun onResponse(
                    call: Call<RegistrationResponse>?,
                    response: Response<RegistrationResponse>?
                ) {
                    _onUserRegistration.value = ServerResponse.SERVER_SUCCESS
                }
            })
        } else {
            _onUserRegistration.value = ServerResponse.PASSWORD_MISMATCH
            password.value = ""
            passwordConfirmation.value = ""
        }
    }

    fun userRegistered() {
        _onUserRegistration.value = ServerResponse.DEFAULT
    }

    private fun initRegistrationRequest(): RegistrationRequest{
        val registrationRequest = RegistrationRequest()
        registrationRequest.username = userId.value.toString()
        registrationRequest.email = email.value.toString()
        registrationRequest.password = password.value.toString()
        return registrationRequest
    }

}