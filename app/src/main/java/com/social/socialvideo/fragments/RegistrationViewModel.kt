package com.social.socialvideo.fragments

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.widget.EditText
import androidx.databinding.ObservableField

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.entities.RegistrationRequest
import com.social.socialvideo.entities.RegistrationResponse
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.retrofit
import com.social.socialvideo.utils.PasswordUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel: ViewModel() {

    // TODO
    lateinit var useId: MutableLiveData<String>

    private val _onUserRegistration = MutableLiveData<Boolean>()
    val onUserRegistration: LiveData<Boolean>
        get() = _onUserRegistration

    //    fun registerUser() {
//
//        if (PasswordUtil.checkMatchingPasswords(binding.password.text.toString(), binding.passwordConfirm.text.toString(), this.context)) {
//
//            val registrationRequest = RegistrationRequest()
//            registrationRequest.username = binding.userID.text.toString()
//            registrationRequest.password = binding.password.text.toString()
//            registrationRequest.email = binding.email.text.toString()
//
//            val apiService = retrofit.create(RestApiService::class.java)
//            val regResponse: Call<RegistrationResponse> = apiService.createUser(registrationRequest)
//
//            regResponse.enqueue(object : Callback<RegistrationResponse> {
//                override fun onFailure(call: Call<RegistrationResponse>?, t: Throwable?) {
//                    println("Fail test")
//                }
//
//                override fun onResponse(call: Call<RegistrationResponse>?, response: Response<RegistrationResponse>?) {
//                    println(response!!.body()!!.token)
//                }
//            })
//            navigateBack()
//        } else {
//            binding.password.text.clear()
//            binding.passwordConfirm.text.clear()
//        }
//    }
        fun registerUser() {
            _onUserRegistration.value = true
        }

    fun userRegistered() {
        _onUserRegistration.value = false
    }

}