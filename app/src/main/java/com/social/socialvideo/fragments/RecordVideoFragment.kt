package com.social.socialvideo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.social.socialvideo.R
import com.social.socialvideo.databinding.RecordBinding
import com.social.socialvideo.viewModels.RecordViewModel


class RecordVideoFragment : Fragment() {
    private lateinit var binding: RecordBinding
    private lateinit var recordViewModel: RecordViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.record, container, false)

        recordViewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        binding.recordViewModel = recordViewModel

        return binding.root
    }


}