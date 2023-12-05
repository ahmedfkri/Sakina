package com.example.sakina.feature_onboarding.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant
import com.example.sakina.databinding.FragmentOnBoardingBinding
import com.example.sakina.feature_onboarding.adapter.ViewPagerAdapter


class OnBoardingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardingBinding
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private var fragmentList = arrayListOf<Fragment>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentList = arrayListOf(
            FirstScreenFragment(), SecondScreenFragment(), ThirdScreenFragment()
        )

        setupAdapter()
        updateView()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateView()
            }
        })


        binding.apply {
            rightArrow.setOnClickListener {
                binding.viewPager.currentItem++
                updateView()
            }
            leftArrow.setOnClickListener {
                viewPager.currentItem--
                updateView()
            }
            btnGetStarted.setOnClickListener {
                MySharedPref.putBool(Constant.ON_BOARDING,true)
                findNavController().navigate(R.id.action_onBoardingFragment_to_signUpFragment)
            }
        }


    }

    private fun updateView() {
        when (binding.viewPager.currentItem) {
            0 -> displayFirstScreen()
            1 -> displaySecondScreen()
            2 -> displayThirdScreen()
        }
    }

    private fun displayThirdScreen() {
        binding.apply {
            leftArrow.isVisible = false
            rightArrow.isVisible = false
            btnGetStarted.isVisible = true
            indicator.setImageResource(R.drawable.ind_third)
        }
    }

    private fun displaySecondScreen() {
        binding.apply {
            leftArrow.isVisible = true
            rightArrow.isVisible = true
            btnGetStarted.isVisible = false
            indicator.setImageResource(R.drawable.ind_second_page)
        }
    }

    private fun displayFirstScreen() {
        binding.apply {
            leftArrow.isVisible = false
            rightArrow.isVisible = true
            btnGetStarted.isVisible = false
            indicator.setImageResource(R.drawable.ind_first)
        }
    }

    private fun setupAdapter() {
        viewPagerAdapter = ViewPagerAdapter(
            fragmentList, requireActivity().supportFragmentManager, lifecycle
        )
        binding.viewPager.apply {
            adapter = viewPagerAdapter
        }
    }


}