package com.social.socialvideo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.ProfileBinding
import com.social.socialvideo.services.SessionManager
import com.social.socialvideo.viewModels.ProfileViewModel
import java.lang.NullPointerException

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        // Slúži na optimálnejšie nájdenie data fieldov pre fragment netreba používať R, findId...
        binding = DataBindingUtil.inflate(
            inflater, R.layout.profile, container, false)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        // Get the viewmodel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.profileViewModel = profileViewModel

        profileViewModel.onLogout.observe(viewLifecycleOwner, Observer { logout ->
            if(logout) {
                resolveUserLogout(logout)
                profileViewModel.resetOnLogout()
            }
        })

        profileViewModel.onPasswordChange.observe(viewLifecycleOwner, Observer { changePwd ->
            if(changePwd) {
                this.findNavController()
                    .navigate(R.id.action_profileFragment_to_changePasswordFragment)
                profileViewModel.resetOnPasswordChange()
            }
        })
        return binding.root
    }

    private fun resolveUserLogout(logout: Boolean) {
        val sessionManager = SessionManager(this.requireContext())
        sessionManager.deleteToken()
        this.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
    }

}