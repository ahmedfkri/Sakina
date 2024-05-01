package com.example.sakina.feature_medicine.domain.use_case

import com.example.sakina.feature_medicine.data.repository.MedicineRepositoryImpl
import com.example.sakina.feature_medicine.domain.model.Medicine

class DeleteMedicineUseCase(val repository: MedicineRepositoryImpl) {

    suspend operator fun invoke(medicine: Medicine) {
        repository.deleteMedicine(medicine)
    }
}