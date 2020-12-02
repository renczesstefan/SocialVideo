package com.social.socialvideo.fragments;

import android.os.Bundle
import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.LoginBinding
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.services.SessionManager
import com.social.socialvideo.viewModels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val sessionManager = SessionManager(this.requireContext())
        binding = DataBindingUtil.inflate(
            inflater, R.layout.login, container, false)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = loginViewModel

        /**
         * Kontrola, ci uz bol uzivatel prihlaseny a teda ci obsahuje sessionManager token
         * Pri logoute tento token mazeme a teda je pouzivatel nuteny sa znova prihlasit
         * */
        if (!sessionManager.fetchAuthToken().isNullOrBlank()) {
            this.findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        } else {

            loginViewModel.onUserLogin.observe(viewLifecycleOwner, Observer { isSuccess ->
                resolveUserLogin(isSuccess)
            })

            loginViewModel.userToken.observe(viewLifecycleOwner, Observer { token ->
                saveUserToken(token)
            })

            loginViewModel.onRegistrationClicked.observe(viewLifecycleOwner, Observer { isClicked ->
                navigateToRegistration(isClicked)
            })

            binding.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root

    }

    private fun saveUserToken(token: String?) {
        val sessionManager = SessionManager(this.requireContext())
         sessionManager.saveAuthToken(token.toString())
    }

    private fun navigateToRegistration(clicked: Boolean) {
        if (clicked){
            this.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            loginViewModel.registrationClicked()
        }
    }

    private fun resolveUserLogin(response: ServerResponse) {
        when (response) {
            ServerResponse.SERVER_SUCCESS -> {
                this.findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                loginViewModel.resetOnUserLogin()
            }
            ServerResponse.CONNECTION_ERROR -> {
                Toast.makeText(context, "Internet connection error!", Toast.LENGTH_SHORT).show()
                loginViewModel.resetOnUserLogin()
            }

            ServerResponse.SERVER_ERROR -> {
                Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
                loginViewModel.resetOnUserLogin()
            }
            else -> {}
        }
    }
}
