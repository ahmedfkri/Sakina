package com.example.sakina.feature_medicine.domain.use_case

class MedicineUseCases(
    val getMedicines: GetMedicinesUseCase,
    val deleteMedicine: DeleteMedicineUseCase,
    val upsertMedicine: UpsertMedicineUseCase,
    val getMedicineById: GetMedicineByIdUseCase
) {
}