package com.example.footballsimulator.onboarding.viewpager.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.footballsimulator.R
import com.example.footballsimulator.databinding.FragmentOnboardingScreenBinding

class OnboardingScreenFragment : Fragment() {

    private var _binding: FragmentOnboardingScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboardingScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setup onboarding screen information text
        arguments?.getInt(STRING_RESOURCE)?.let { stringResource ->
            binding.tvInformation.text = getString(stringResource)
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.navigate_from_onboarding_to_fixtures)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val STRING_RESOURCE = "string_resource"

        @JvmStatic
        fun newInstance(@StringRes stringResource: Int): OnboardingScreenFragment {
            return OnboardingScreenFragment().apply {
                arguments = Bundle().apply {
                    putInt(STRING_RESOURCE, stringResource)
                }
            }
        }
    }
}
