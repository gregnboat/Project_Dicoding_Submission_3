package com.greig.submission3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greig.submission3.FollowingAdapter
import com.greig.submission3.FollowingViewModel
import com.greig.submission3.R
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: FollowingAdapter

    companion object {
        const val  EXTRA_USERNAME = "username"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFollowing.setHasFixedSize(true)
        showListFollowing()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        followingViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followingItem ->
            if (followingItem != null) {
                adapter.setData(followingItem)
                fg_progress_bar.visibility = View.GONE
            }
        })

        if (arguments != null) {
            val username = arguments?.getString(EXTRA_USERNAME)
            followingViewModel.setFollowing(username)
        }
    }

    private fun showListFollowing() {
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()
        rvFollowing.adapter = adapter
    }
}