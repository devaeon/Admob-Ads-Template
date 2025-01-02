package com.devaeon.adsTemplate

import android.os.Bundle
import android.util.Log
import android.util.Log.v
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.devaeon.adsTemplate.databinding.FragmentFirstBinding
import com.devaeon.adsTemplate.ui.adsConfig.AdmobAdsViewModel
import com.devaeon.feature.revenue.domain.model.AdState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AdmobAdsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                viewModel.adsState.collectLatest {
                    Log.i(TAG, "onCreate: adState: $it")

                    when (it) {
                        AdState.NOT_INITIALIZED -> {}
                        AdState.INITIALIZED -> {}
                        AdState.LOADING -> {}
                        AdState.READY -> binding.buttonFirst.isEnabled = true
                        AdState.SHOWING -> {}
                        AdState.VALIDATED -> {
                            binding.buttonFirst.isEnabled = false
                            viewModel.loadAdIfNeeded(requireContext())
                        }
                        AdState.ERROR -> {}
                    }
                }
            }
        }

        binding.buttonFirst.setOnClickListener {
            viewModel.showAd(requireActivity())
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private const val TAG = "FirstFragment"