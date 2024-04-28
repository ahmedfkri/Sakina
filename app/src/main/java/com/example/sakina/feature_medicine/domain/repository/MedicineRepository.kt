package com.example.sakina.feature_medicine.domain.repository

import com.example.sakina.feature_medicine.domain.model.Medicine
import kotlinx.coroutines.flow.Flow

interface MedicineRepository {

    suspend fun upsertMedicine(medicine: Medicine)

    suspend fun deleteMedicine(medicine: Medicine)

    fun getAllMedicines(): Flow<List<Medicine>>

    suspend fun getMedicineById(medicineId: Long): Medicine?

    suspend fun getLastMedicine(): Medicine?



}