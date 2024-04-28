package com.example.sakina.feature_medicine.domain.use_case

import com.example.sakina.feature_medicine.data.repository.MedicineRepositoryImpl
import com.example.sakina.feature_medicine.domain.model.InvalidMedicineException
import com.example.sakina.feature_medicine.domain.model.Medicine
import kotlin.jvm.Throws

class UpsertMedicineUseCase(val repository: MedicineRepositoryImpl) {

    @Throws(InvalidMedicineException::class)
    suspend operator fun invoke(medicine: Medicine) {

        if (medicine.name.isBlank()) {
            throw InvalidMedicineException("Name can't be empty")
        }
        if (medicine.dosage == 0) {
            throw InvalidMedicineException("Please specify the dosage")
        }



        repository.upsertMedicine(medicine)

    }
}