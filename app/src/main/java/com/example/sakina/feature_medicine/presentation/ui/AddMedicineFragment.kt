package com.example.sakina.feature_medicine.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sakina.MainActivity
import com.example.sakina.core.util.Constant.PILL_1
import com.example.sakina.core.util.Constant.PILL_2
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.databinding.FragmentAddMedicineBinding
import com.example.sakina.feature_medicine.domain.model.InvalidMedicineException
import com.example.sakina.feature_medicine.domain.model.Medicine
import com.example.sakina.feature_medicine.presentation.view_model.MedicineViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddMedicineFragment : Fragment() {

    private lateinit var binding: FragmentAddMedicineBinding
    private lateinit var medicineViewModel: MedicineViewModel
    private var imageId = PILL_1
    private var currentInputNumber = 1
//    lateinit var picker: MaterialTimePicker
    private val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMedicineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicineViewModel = (activity as MainActivity).medicineViewModel

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select reminder time")
            .build()

        var selectedEditText: TextInputEditText? = null

        val timeEditTextList = listOf(
            binding.edtTime1,
            binding.edtTime2,
            binding.edtTime3,
            binding.edtTime4,
            binding.edtTime5
        )

        timeEditTextList.forEach { editText ->
            editText.setOnClickListener {
                selectedEditText = editText // Store the clicked EditText
                picker.show(parentFragmentManager, null)
            }
        }

        picker.addOnPositiveButtonClickListener {
            selectedEditText?.let { editText ->
                val hour = picker.hour
                val minute = picker.minute
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hour)
                selectedTime.set(Calendar.MINUTE, minute)
                editText.setText(sdf.format(selectedTime.time))
            }
        }

        binding.rgImage.setOnCheckedChangeListener { _, checkedId ->
            imageId = when (checkedId) {
                binding.rbPill1.id -> PILL_1
                binding.rbPill2.id -> PILL_2
                else -> PILL_1
            }
        }



        binding.btnConfirm.setOnClickListener {
            try {
                val name = binding.edtMedicineName.text.toString()
                val dosage = binding.edtDosage.text.toString().toInt()

                val reminderTimes = timeEditTextList
                    .filter { it.text!!.isNotBlank() }
                    .map { editText ->
                        parseTime(editText.text.toString())
                    }

                val medicine = Medicine(
                    name = name,
                    dosage = dosage,
                    isTaken = false,
                    reminderTimes = reminderTimes,
                    imageId = imageId
                )

                lifecycleScope.launch {
                    medicineViewModel.upsertMedicine(medicine)
                    Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: InvalidMedicineException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(activity, "Unexpected error occurred", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnAddTime.setOnClickListener {
            showNextTextInput()
        }
    }


    private fun parseTime(timeText: String): Date {
        return sdf.parse(timeText) ?: throw IllegalArgumentException("Invalid time format")
    }

    private fun showNextTextInput() {
        val timeLayoutList = listOf(
            binding.loTime2,
            binding.loTime3,
            binding.loTime4,
            binding.loTime5
        )

        if (currentInputNumber < timeLayoutList.size + 1) {
            timeLayoutList[currentInputNumber - 1].isVisible = true
            currentInputNumber++
        }

        if (currentInputNumber == timeLayoutList.size + 1) {
            binding.btnAddTime.isVisible = false
        }
    }
}

