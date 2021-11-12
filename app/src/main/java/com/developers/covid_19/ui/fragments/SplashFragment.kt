package com.developers.covid_19.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.developers.covid_19.R
import com.developers.covid_19.databinding.HomeFragmentBinding
import com.developers.covid_19.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment:Fragment() {

    private var _binding: SplashFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardViewStartNow.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_splashFragment_to_homeFragment,
                savedInstanceState,
                navOptions
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}