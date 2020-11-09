package com.social.socialvideo.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.LoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.login, container, false)
        binding.loginModel = this

        return binding.root
    }

    fun onRegistrationClick() {
        this.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    fun onLoginClick() {
        this.findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
    }

}
