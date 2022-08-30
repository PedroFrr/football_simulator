package com.example.footballsimulator.onboarding.viewpager.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.footballsimulator.R
import com.example.footballsimulator.common.util.UiEvent
import com.example.footballsimulator.databinding.FragmentOnboardingScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingScreenFragment : Fragment() {

    private var _binding: FragmentOnboardingScreenBinding? = null
    private val binding get() = _binding!!

    private val onboardingScreenViewModel by viewModels<OnboardingScreenViewModel>()

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

        setupUi()
        setupObservables()
    }

    private fun setupUi() {
        // setup onboarding screen information text
        arguments?.getInt(STRING_RESOURCE)?.let { stringResource ->
            binding.tvInformation.text = getString(stringResource)
        }

        binding.btnNext.setOnClickListener {
            onboardingScreenViewModel.onDoneButtonClicked()
        }
    }

    private fun setupObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                onboardingScreenViewModel.uiEvent.collect { uiEvent ->
                    when (uiEvent) {
                        UiEvent.Success -> findNavController().navigate(R.id.navigate_from_onboarding_to_fixtures)
                        else -> Unit
                    }
                }
            }
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
