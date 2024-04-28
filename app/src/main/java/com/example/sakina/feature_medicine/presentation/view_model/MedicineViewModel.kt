package com.example.sakina.feature_medicine.presentation.view_model

import androidx.lifecycle.ViewModel
import com.example.sakina.feature_medicine.domain.model.Medicine
import com.example.sakina.feature_medicine.domain.use_case.MedicineUseCases
import kotlinx.coroutines.flow.Flow

class MedicineViewModel(
    private val medicineUseCases: MedicineUseCases
) : ViewModel() {


    fun getAllMedicines(): Flow<List<Medicine>> {
        return medicineUseCases.getMedicines()
    }

    suspend fun upsertMedicine(medicine: Medicine) {
        medicineUseCases.upsertMedicine(medicine)
    }

    suspend fun deleteMedicine(medicine: Medicine) {
        medicineUseCases.deleteMedicine(medicine)
    }

    suspend fun getMedicineById(medicineId: Long): Medicine? {
        return medicineUseCases.getMedicineById(medicineId)
    }

    fun setReminders(medicine: Medicine) {
        return medicineUseCases.setRemindersUseCase(medicine)
    }

    suspend fun getLastMedicine(): Medicine? {
        return medicineUseCases.getLastMedicineUseCase()
    }


}