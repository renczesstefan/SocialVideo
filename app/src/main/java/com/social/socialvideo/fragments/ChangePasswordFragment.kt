package com.social.socialvideo.fragments

import android.os.Bundle
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
import com.social.socialvideo.databinding.ChangePasswordBinding
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.services.SessionManager
import com.social.socialvideo.viewModels.ChangePasswordViewModel

class ChangePasswordFragment : Fragment() {
    private lateinit var binding: ChangePasswordBinding
    private lateinit var changePasswordViewModel: ChangePasswordViewModel
    lateinit private var sessionManager: SessionManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.change_password, container, false)
        sessionManager = SessionManager(this.requireContext())
        // Get the viewmodel
        changePasswordViewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
        changePasswordViewModel.token = sessionManager.fetchAuthToken().toString()
        binding.changePasswordViewModel = changePasswordViewModel

        changePasswordViewModel.onChange.observe(viewLifecycleOwner, Observer { changeResponse ->
            if(changeResponse != ServerResponse.DEFAULT){
                resolveOnChangeResponse(changeResponse)
                changePasswordViewModel.resetOnChange()
            }
        })

        changePasswordViewModel.newToken.observe(viewLifecycleOwner, Observer { token ->
            saveUserToken(token)
        })
        changePasswordViewModel.newRefreshToken.observe(viewLifecycleOwner, Observer { token ->
            saveUserRefreshToken(token)
        })
        return binding.root
    }

    private fun saveUserRefreshToken(token: String) {
        sessionManager.saveRefreshToken(token.toString())
    }

    private fun resolveOnChangeResponse(changeResponse: ServerResponse) {
        when (changeResponse){
            ServerResponse.SERVER_SUCCESS -> {
                this.findNavController().navigate(R.id.action_changePasswordFragment_to_profileFragment)
                Toast.makeText(context, "Password successfully changed!", Toast.LENGTH_SHORT).show()
            }
            ServerResponse.SERVER_ERROR -> {
                Toast.makeText(context, "Password change failed!", Toast.LENGTH_SHORT).show()
            }
            ServerResponse.PASSWORD_MISMATCH -> {
                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    private fun saveUserToken(token: String?) {
        sessionManager.saveAuthToken(token.toString())
    }

}