package com.social.socialvideo.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.util.Util
import com.google.firebase.crashlytics.internal.common.Utils
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.rest.entities.CheckUsernameRequest
import com.social.socialvideo.rest.entities.CheckUsernameResponse
import com.social.socialvideo.rest.entities.RegistrationRequest
import com.social.socialvideo.rest.entities.RegistrationResponse
import com.social.socialvideo.rest.services.RestApiService
import com.social.socialvideo.rest.services.retrofit
import com.social.socialvideo.utils.PasswordUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom

class RegistrationViewModel : ViewModel() {

    var userId = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var passwordConfirmation = MutableLiveData<String>()
    var _onUserRegistration = MutableLiveData<ServerResponse>()
    val onUserRegistration: LiveData<ServerResponse>
        get() = _onUserRegistration

    fun registerUser() {
        val apiService = retrofit.create(
            RestApiService::class.java)
        val checkUsernameRequest = CheckUsernameRequest()
        checkUsernameRequest.username = userId.value.toString()
        val checkUsernameResponse: Call<CheckUsernameResponse> =
            apiService.checkUsername(checkUsernameRequest)

        checkUsernameResponse.enqueue(object : Callback<CheckUsernameResponse> {
            override fun onFailure(call: Call<CheckUsernameResponse>?, t: Throwable?) {
                _onUserRegistration.value = ServerResponse.CONNECTION_ERROR
            }
            override fun onResponse(call: Call<CheckUsernameResponse>?, response: Response<CheckUsernameResponse>?) {
                if (response?.code() == 200 && response.body()!!.exists) {
                    _onUserRegistration.value = ServerResponse.USER_ALREADY_EXISTS
                } else {
                    if (_onUserRegistration.value != ServerResponse.USER_ALREADY_EXISTS && !PasswordUtil.checkMatchingPasswords(password.value, passwordConfirmation.value)) {
                        _onUserRegistration.value = ServerResponse.PASSWORD_MISMATCH
                        password.value = ""
                        passwordConfirmation.value = ""
                    } else if (_onUserRegistration.value != ServerResponse.USER_ALREADY_EXISTS) {
                        val registrationRequest = initRegistrationRequest()
                        val regResponse: Call<RegistrationResponse> = apiService.createUser(registrationRequest)
                        regResponse.enqueue(object : Callback<RegistrationResponse> {
                            override fun onFailure(call: Call<RegistrationResponse>?, t: Throwable?) {
                                _onUserRegistration.value = ServerResponse.SERVER_ERROR
                            }

                            override fun onResponse(call: Call<RegistrationResponse>?,response: Response<RegistrationResponse>?) {
                                _onUserRegistration.value = ServerResponse.SERVER_SUCCESS
                            }
                        })
                    }
                }
            }
        })



    }

    fun resetOnUserRegistration() {
        _onUserRegistration.value = ServerResponse.DEFAULT
    }

    private fun initRegistrationRequest(): RegistrationRequest {
        val registrationRequest = RegistrationRequest()
        registrationRequest.username = userId.value.toString()
        registrationRequest.email = email.value.toString()
        registrationRequest.password = PasswordUtil.hashPwd(password.value.toString())
        return registrationRequest
    }
}