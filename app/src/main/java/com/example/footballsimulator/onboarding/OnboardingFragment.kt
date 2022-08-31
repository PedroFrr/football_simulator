package com.example.footballsimulator.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.footballsimulator.databinding.FragmentOnboardingBinding
import com.example.footballsimulator.onboarding.viewpager.OnboardingViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private val onboardingScreenViewModel by activityViewModels<OnboardingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservables()
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                onboardingScreenViewModel.uiState.collect { uiState ->
                    setupViewPager(uiState.onboardingScreenInformation)
                }
            }
        }
    }

    private fun setupViewPager(screenInformation: List<Int>) {
        val viewPagerAdapter = OnboardingViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            screenInformation
        )

        binding.viewpagerOnboarding.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpagerOnboarding) { tab, position ->
            //Some implementation
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
