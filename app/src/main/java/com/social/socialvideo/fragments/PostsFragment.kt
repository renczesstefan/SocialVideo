package com.social.socialvideo.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.exoplayer2.SimpleExoPlayer
import com.social.socialvideo.R
import com.social.socialvideo.adapters.UserPostsAdapter
import com.social.socialvideo.databinding.PostsBinding
import com.social.socialvideo.domain.UserPost
import com.social.socialvideo.viewModels.PostsViewModel


class PostsFragment : Fragment() {

    private lateinit var binding: PostsBinding
    private lateinit var postsViewModel: PostsViewModel
    private var userPostsAdapter: UserPostsAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.posts, container, false
        )
        val activity = requireNotNull(this.activity) {
            "Only no null activity is required"
        }

        // Vizualne oddelenie jednotlivych postov
        val dividerItemDecoration = DividerItemDecoration(
            binding.userPosts.context, 1)
        binding.userPosts.addItemDecoration(dividerItemDecoration)

        postsViewModel = ViewModelProvider(this, PostsViewModel.Factory(activity.application)).get(PostsViewModel::class.java)
        binding.postsViewModel = postsViewModel
        userPostsAdapter = UserPostsAdapter(requireActivity())
        binding.userPosts.adapter = userPostsAdapter

        postsViewModel.onUserRecordsNavigate.observe(viewLifecycleOwner, Observer { userPosts ->
            if (userPosts) {
                this.findNavController()
                    .navigate(R.id.action_postsFragment_to_newPostFragment)
                postsViewModel.resetRecordVideoNavigate()
            }
        })

        postsViewModel.onUserProfileNavigate.observe(viewLifecycleOwner, Observer { userProfile ->
            if (userProfile) {
                this.findNavController()
                    .navigate(R.id.action_postsFragment_to_profileFragment)
                postsViewModel.resetUserProfileNavigate()
            }
        })

        SimpleExoPlayer.Builder(requireContext()).build()
        return binding.root
    }

    /**
     * Funkcia ktora je volana po tom ako sa vytvori fragmentova hierarchia
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Observujeme livedata ktore vracaju vsetky videa z databazy
        postsViewModel.postList.observe(viewLifecycleOwner, Observer<List<UserPost>> { videos ->
            videos?.apply {
                userPostsAdapter?.data = videos
            }
        })
    }


}