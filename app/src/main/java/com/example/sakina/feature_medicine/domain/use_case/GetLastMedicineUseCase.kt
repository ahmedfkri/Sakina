package com.example.sakina.feature_medicine.domain.use_case

import com.example.sakina.feature_medicine.data.repository.MedicineRepositoryImpl
import com.example.sakina.feature_medicine.domain.model.Medicine

class GetLastMedicineUseCase(val repository: MedicineRepositoryImpl) {

    suspend operator fun invoke(): Medicine? {
        return repository.getLastMedicine()
    }
}