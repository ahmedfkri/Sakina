package com.example.sakina

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.ADVICE_BASE_URL
import com.example.sakina.core.util.Constant.FIRST_NAME
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentHomeBinding
import com.example.sakina.feature_advice.data.repository.AdviceRepositoryImpl
import com.example.sakina.feature_advice.domain.model.Advice
import com.example.sakina.feature_advice.domain.use_case.GetAdviceByIdUseCase
import com.example.sakina.feature_advice.domain.use_case.GetTotalAdvicesCountUseCase
import com.example.sakina.feature_advice.presentation.view_model.AdviceViewModel
import com.example.sakina.feature_advice.presentation.view_model.AdviceViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var adviceURL = ADVICE_BASE_URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.heartCheck.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_heartCheckingFragment)
        }

        val callback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {}
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        binding.cardHealthTip.setOnClickListener {
            openAdviceWebView()
        }
        binding.txtUserName.text = MySharedPref.getString(FIRST_NAME, "User")
        binding.cardSkin.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_skinFragment)
        }
        binding.cardSkin.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_skinFragment)
        }

        binding.imgSakina.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_chatWithSakinaFragment)
        }

        showAdviceData()


    }

    override fun onResume() {
        super.onResume()
        showAdviceData()

    }

    private fun openAdviceWebView() {
        val bundle = Bundle().apply {
            putString("adviceURL", adviceURL)
        }
        findNavController().navigate(R.id.action_homeFragment_to_adviceFragment, bundle)
    }

    private fun showAdviceData() {
        val gson = Gson()
        val adviceJson = MySharedPref.getString("advice", "")
        val advice = gson.fromJson(adviceJson, Advice::class.java)

        Handler().postDelayed({
            advice?.let {
                adviceURL += it.id
            }
        }, 1000)

        binding.txtAdviceTitle.text = advice?.title ?: "None"
        Glide.with(this@HomeFragment).load(advice?.imageUrl)
            .into(binding.imgAdvice)

    }
}