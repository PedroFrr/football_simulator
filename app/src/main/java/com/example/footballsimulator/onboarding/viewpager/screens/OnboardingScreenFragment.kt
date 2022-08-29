package com.example.footballsimulator.onboarding.viewpager.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
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

        arguments?.getInt(STRING_RESOURCE)?.let { stringResource ->
            binding.tvInformation.text = getString(stringResource)
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
