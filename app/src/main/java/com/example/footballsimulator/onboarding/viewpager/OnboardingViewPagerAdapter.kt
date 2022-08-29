package com.example.footballsimulator.onboarding.viewpager

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.footballsimulator.onboarding.viewpager.screens.OnboardingScreenFragment

/**
 * A [FragmentStateAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class OnboardingViewPagerAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val onboardingScreenStringResourceList: List<Int>
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = onboardingScreenStringResourceList.count()

    override fun createFragment(position: Int) = OnboardingScreenFragment.newInstance(onboardingScreenStringResourceList[position])
}