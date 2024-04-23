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
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.databinding.FragmentMedicineListBinding
import com.example.sakina.feature_medicine.presentation.adapter.MedicineAdapter
import com.example.sakina.feature_medicine.presentation.view_model.MedicineViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MedicineListFragment : Fragment() {

    lateinit var binding: FragmentMedicineListBinding
    lateinit var medicineViewModel: MedicineViewModel

    private lateinit var medicineAdapter: MedicineAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedicineListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        medicineViewModel = (activity as MainActivity).medicineViewModel

        setupRecyclerView()
        medicineAdapter.setOnItemClickListener { medicine ->
            Toast.makeText(requireContext(), medicine.name, Toast.LENGTH_SHORT).show()

            val bundle = Bundle().apply {
                putLong("medicineId", medicine.id)
            }
            findNavController().navigate(
                R.id.action_medicineListFragment_to_medicineDetailsFragment,
                bundle
            )
        }

        lifecycleScope.launch {
            medicineViewModel.getAllMedicines().collect() { medicines ->
                if (medicines.isEmpty()) {
                    Log.d(TAG, "onViewCreated: $medicines")
                    Toast.makeText(activity, "No Medicine", Toast.LENGTH_SHORT).show()

                } else {
                    Log.d(TAG, "onViewCreated: $medicines")
                    medicineAdapter.differ.submitList(medicines)
                }
            }
        }




        binding.fabAddMedicine.setOnClickListener {
            findNavController().navigate(R.id.action_medicineListFragment_to_addMedicineFragment)
        }


    }

    private fun setupRecyclerView() {
        medicineAdapter = MedicineAdapter()
        binding.rvMedicine.adapter = medicineAdapter
    }
}