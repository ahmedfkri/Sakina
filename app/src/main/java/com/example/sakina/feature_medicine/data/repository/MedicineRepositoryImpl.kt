package com.example.sakina.feature_medicine.data.repository

import com.example.sakina.feature_medicine.data.local.MedicineDataBase
import com.example.sakina.feature_medicine.domain.model.Medicine
import com.example.sakina.feature_medicine.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.Flow

class MedicineRepositoryImpl(private val db: MedicineDataBase) : MedicineRepository {

    override suspend fun upsertMedicine(medicine: Medicine) {
        return db.getDao().upsertMedicine(medicine)
    }

    override suspend fun deleteMedicine(medicine: Medicine) {
        return db.getDao().deleteMedicine(medicine)
    }

    override fun getAllMedicines(): Flow<List<Medicine>> {
        return db.getDao().getAllMedicines()
    }

    override suspend fun getMedicineById(medicineId: Long): Medicine? {
        return db.getDao().getMedicineById(medicineId)
    }


}