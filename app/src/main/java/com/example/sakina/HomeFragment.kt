package com.example.sakina

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentHomeBinding
import com.example.sakina.feature_advice.data.repository.AdviceRepositoryImpl
import com.example.sakina.feature_advice.domain.use_case.GetAdviceByIdUseCase
import com.example.sakina.feature_advice.domain.use_case.GetTotalAdvicesCountUseCase
import com.example.sakina.feature_advice.presentation.ui.view_model.AdviceViewModel
import com.example.sakina.feature_advice.presentation.ui.view_model.AdviceViewModelFactory
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var adviceViewModel: AdviceViewModel
    var count = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        //Advice
        val adviceRepository = AdviceRepositoryImpl()

        val getTotalAdvicesCountUseCase = GetTotalAdvicesCountUseCase(adviceRepository)

        val getAdviceByIdUseCase = GetAdviceByIdUseCase(adviceRepository)

        adviceViewModel = ViewModelProvider(
            this,
            AdviceViewModelFactory(getTotalAdvicesCountUseCase, getAdviceByIdUseCase)
        ).get(AdviceViewModel::class.java)

        getAdvicesCount()


    }

    private fun showAdvice(count: Int) {
        val adviceId = (1 until count).random()
        lifecycleScope.launch {
            adviceViewModel.getAdvicesById(adviceId).collect { resource ->

                when (resource) {
                    is Resource.Success -> {
                        val advice =resource.data
                        binding.txtAdviceTitle.text = advice?.title ?: "None"
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                        Log.d("Advices test", "getAdvice: ${resource.message} ")
                    }

                    else -> {

                    }
                }

            }
        }
    }

    private fun getAdvicesCount() {
        lifecycleScope.launch {
            adviceViewModel.getTotalAdvicesCount().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        count = resource.data ?: 1
                        binding.txtAdviceTitle.text = count.toString()
                        showAdvice(count)
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                            .show()
                        Log.d("Advices test", "getAdvice: ${resource.message} ")
                    }

                    else -> {

                    }

                }

            }
        }
    }

}