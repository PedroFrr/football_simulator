package com.example.footballsimulator.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.footballsimulator.R
import com.example.footballsimulator.databinding.FragmentOnboardingBinding
import com.example.footballsimulator.onboarding.viewpager.OnboardingViewPagerAdapter

class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
    }

    private fun setupUi() {
        setupViewPager()
    }

    private fun setupViewPager() {
        val onboardingScreenStringResourceList = listOf(
            R.string.onboarding_first_screen_information,
            R.string.onboarding_second_screen_information
        )

        val viewPagerAdapter = OnboardingViewPagerAdapter(
            requireActivity().supportFragmentManager,
            lifecycle,
            onboardingScreenStringResourceList
        )

        binding.viewpagerOnboarding.adapter = viewPagerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
