package com.example.sakina.feature_advice.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.sakina.databinding.FragmentAdviceBinding


class AdviceFragment : Fragment() {

    lateinit var binding: FragmentAdviceBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdviceBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = arguments?.getString("adviceURL", "NON")
        binding.adviceWebView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            url?.let { loadUrl(url) }
        }

    }


}