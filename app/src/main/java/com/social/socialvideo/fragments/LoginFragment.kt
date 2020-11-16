package com.social.socialvideo.fragments;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.LoginBinding
import com.social.socialvideo.viewModels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.login, container, false)
        // Get the viewmodel
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel

        loginViewModel.onLoginClicked.observe(viewLifecycleOwner, Observer { isSuccess ->
            resolveUserLogin(isSuccess)
        })

        loginViewModel.onRegistrationClicked.observe(viewLifecycleOwner, Observer { isClicked ->
            navigateToRegistration(isClicked)
        })

        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    private fun navigateToRegistration(clicked: Boolean) {
        if (clicked){
            this.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            loginViewModel.registrationClicked()
        }
    }

    private fun resolveUserLogin(isSuccess: Boolean) {
        if (isSuccess) {
            this.findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
            loginViewModel.loggedIn()
        }
    }

}
