package com.example.bookkeeper.main_flow

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.bookkeeper.R
import com.example.bookkeeper.base_classes.BaseFragment
import com.example.bookkeeper.databinding.FragmentLauncherBinding

class LauncherFragment : BaseFragment<LauncherViewModel>() {

    private var launcheBinding: FragmentLauncherBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launcheBinding = FragmentLauncherBinding.bind(view)
        Handler().postDelayed({
            Navigation.findNavController(view).navigate(R.id.action_launcherFragment_to_booksSearchFragment)
        }, 3000)
    }

}