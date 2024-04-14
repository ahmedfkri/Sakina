package com.example.sakina.feature_account.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.data.MySharedPref
import com.example.sakina.core.util.Constant.FIRST_NAME
import com.example.sakina.core.util.Constant.LAST_NAME
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.Resource
import com.example.sakina.databinding.FragmentChangeNameBinding
import com.example.sakina.feature_account.domain.model.ChangeNameRequest
import com.example.sakina.feature_account.presentation.view_model.AccountViewModel
import kotlinx.coroutines.launch

class ChangeNameFragment : Fragment() {
    lateinit var binding: FragmentChangeNameBinding
    private lateinit var viewModel: AccountViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).accountViewModel

        showData()


        binding.confirmBtn.setOnClickListener {

            val firstName = binding.edtFirstName.text.toString()
            val lastName = binding.edtLastName.text.toString()
            val changeNameRequest = ChangeNameRequest(firstName, lastName)
            changeName(changeNameRequest)


        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_changeNameFragment_to_accountFragment)
        }
    }

    private fun showData() {
        binding.edtFirstName.setText(MySharedPref.getString(FIRST_NAME,""))
        binding.edtLastName.setText(MySharedPref.getString(LAST_NAME,""))
    }

    private fun changeName(request: ChangeNameRequest) {
        lifecycleScope.launch {
            viewModel.changeName(request).collect{ resource->

                when(resource){
                    is Resource.Success ->{
                        Log.d(TAG, "changeName: success "+ resource.data)

                    }
                    is Resource.Error ->{
                        Log.d(TAG, "changeName: ERROR "+ resource.message)

                    }
                    else ->{
                        Log.d(TAG, "changeName: else branch")
                    }
                }
            }
        }

    }
}