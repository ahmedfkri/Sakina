package com.example.sakina.feature_medicine.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant.PILL_1
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.core.util.DateTime
import com.example.sakina.databinding.FragmentMedicineDetailsBinding
import com.example.sakina.feature_medicine.domain.model.Medicine
import com.example.sakina.feature_medicine.presentation.view_model.MedicineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MedicineDetailsFragment : Fragment() {

    lateinit var binding: FragmentMedicineDetailsBinding
    private lateinit var medicineViewModel: MedicineViewModel
    private var isTaken = false
    private var medicine: Medicine? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicineDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicineViewModel = (activity as MainActivity).medicineViewModel


        val medicineId = arguments?.getLong("medicineId", 1)

        runBlocking {
            try {
                medicine = medicineViewModel.getMedicineById(medicineId!!)
                isTaken = medicine!!.isTaken
                val imageId = when (medicine!!.imageId) {
                    PILL_1 -> R.drawable.ic_pill1
                    else -> R.drawable.ic_pill2
                }

                binding.apply {
                    txtMedicineName.text = medicine?.name
                    txtDate.text = DateTime().getCurrentDateTime(System.currentTimeMillis())
                    if (medicine!!.isTaken) {
                        toggleIsTaken.check(btnTaken.id)
                    } else {
                        toggleIsTaken.check(btnSkipped.id)
                    }

                    Glide.with(requireActivity()).load(imageId).centerCrop().into(imgMedicine)

                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.toggleIsTaken.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    binding.btnSkipped.id -> isTaken = false
                    binding.btnTaken.id -> isTaken = true
                }
            }


        }


        binding.btnConfirm.setOnClickListener {

            lifecycleScope.launch {
                try {
                    medicineViewModel.upsertMedicine(
                        Medicine(
                            medicine!!.id, medicine!!.name, medicine!!.dosage,
                            isTaken = isTaken, imageId = 1, reminderTimes = null
                        )
                    )
                    Log.d(TAG, medicine.toString())
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }

            }

        }


    }

}