package com.example.sakina.feature_medicine.domain.use_case

import com.example.sakina.feature_medicine.data.repository.MedicineRepositoryImpl
import com.example.sakina.feature_medicine.domain.model.Medicine

class GetMedicineByIdUseCase(val repository: MedicineRepositoryImpl) {

    suspend operator fun invoke(medicineId: Long): Medicine? {
        return repository.getMedicineById(medicineId)
    }

}