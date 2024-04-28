package com.example.sakina.feature_medicine.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.sakina.MainActivity
import com.example.sakina.R
import com.example.sakina.core.util.Constant.TAG
import com.example.sakina.databinding.FragmentMedicineListBinding
import com.example.sakina.feature_medicine.presentation.adapter.MedicineAdapter
import com.example.sakina.feature_medicine.presentation.view_model.MedicineViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MedicineListFragment : Fragment() {

    lateinit var binding: FragmentMedicineListBinding
    lateinit var medicineViewModel: MedicineViewModel

    private lateinit var itemTouchHelperCallback: ItemTouchHelper.Callback


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
        getMedicines()
        setUpSwipeActions()


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

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }





        binding.fabAddMedicine.setOnClickListener {
            findNavController().navigate(R.id.action_medicineListFragment_to_addMedicineFragment)
        }


    }

    private fun setupRecyclerView() {
        medicineAdapter = MedicineAdapter()
        binding.rvMedicine.adapter = medicineAdapter
    }

    private fun getMedicines() {
        lifecycleScope.launch {
            medicineViewModel.getAllMedicines().collect() { medicines ->
                if (medicines.isEmpty()) {
                    Log.d(TAG, "onViewCreated: $medicines")
                    binding.imgNoMedicine.isVisible = true

                } else {
                    Log.d(TAG, "onViewCreated: $medicines")
                    binding.imgNoMedicine.isVisible = false
                    medicineAdapter.differ.submitList(medicines)
                }
            }
        }
    }


    private fun setUpSwipeActions() {
        itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val medicine = medicineAdapter.differ.currentList[position]

                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    Snackbar.make(requireView(), "Delete ${medicine.name}?", Snackbar.LENGTH_LONG)
                        .setAction("Undo") {
                            getMedicines()
                        }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                if (event != DISMISS_EVENT_ACTION) {

                                    runBlocking { medicineViewModel.deleteMedicine(medicine) }
                                    getMedicines()
                                }
                            }
                        }).show()
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMedicine)
    }
}