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
import androidx.navigation.fragment.findNavController
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant.PILL_1
import com.example.sakina.core.util.Constant.PILL_2
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.databinding.FragmentAddMedicineBinding
import com.example.sakina.feature_medicine.domain.model.InvalidMedicineException
import com.example.sakina.feature_medicine.domain.model.Medicine
import com.example.sakina.feature_medicine.presentation.view_model.MedicineViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddMedicineFragment : Fragment() {

    private lateinit var binding: FragmentAddMedicineBinding
    private lateinit var medicineViewModel: MedicineViewModel

    lateinit var timeLayouts: List<TextInputLayout>

    var selectedTimes = mutableListOf<Long>()

    private var imageId = PILL_1
    private var currentInputNumber = 1

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

        val picker = buildTimePicker()

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


        timeLayouts = listOf(
            binding.loTime1,
            binding.loTime2,
            binding.loTime3,
            binding.loTime4,
            binding.loTime5
        )



        timeLayouts.forEach { lo ->
            lo.setEndIconOnClickListener {
                lo.isVisible = false
                --currentInputNumber
                binding.btnAddTime.isVisible = currentInputNumber <= timeLayouts.size
            }
        }

        picker.addOnPositiveButtonClickListener {
            selectedEditText?.let { editText ->
                val hour = picker.hour
                val minute = picker.minute
                val selectedTime = Calendar.getInstance(TimeZone.getDefault())
                selectedTime[Calendar.HOUR_OF_DAY] = hour
                selectedTime[Calendar.MINUTE] = minute
                selectedTimes.add(selectedTime.timeInMillis)
                Log.d(TAG, "onViewCreated: " + selectedTime.time)
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

                val medicine = Medicine(
                    name = name,
                    dosage = dosage,
                    isTaken = false,
                    reminderTimes = selectedTimes,
                    lastTimeUpdated = null,
                    imageId = imageId
                )

                lifecycleScope.launch {
                    medicineViewModel.upsertMedicine(medicine)
                    Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                    if (selectedTimes.isNotEmpty()) {
                        val insertedMedicine = medicineViewModel.getLastMedicine()
                        medicineViewModel.setReminders(insertedMedicine!!)
                        findNavController().navigateUp()
                    }


                }
            } catch (e: InvalidMedicineException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnAddTime.setOnClickListener {
            checkCurrentInputNumber()
        }
    }


    private fun buildTimePicker() = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(12)
        .setMinute(0)
        .setTitleText("Select reminder time")
        .build()


    private fun checkCurrentInputNumber() {
        if (currentInputNumber < timeLayouts.size + 1) {
            timeLayouts[currentInputNumber].isVisible = true
            currentInputNumber++
        }

        if (currentInputNumber == timeLayouts.size) {
            binding.btnAddTime.isVisible = false
        }
    }
}

