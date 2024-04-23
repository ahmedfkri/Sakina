package com.example.sakina.feature_medicine.domain.use_case

import com.example.sakina.feature_medicine.data.repository.MedicineRepositoryImpl
import com.example.sakina.feature_medicine.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

class GetMedicinesUseCase(val repository: MedicineRepositoryImpl) {

    operator fun invoke(): Flow<List<Medicine>> {
        return repository.getAllMedicines()
    }
}