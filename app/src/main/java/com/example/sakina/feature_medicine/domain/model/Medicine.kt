package com.example.sakina.feature_medicine.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sakina.feature_medicine.data.local.Converters
import java.lang.Exception

@Entity(tableName = "medicines")
data class Medicine(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dosage: Int,
    @TypeConverters(Converters::class)
    val reminderTimes: List<Long>?,
    val lastTimeUpdated: Long?,
    var isTaken: Boolean,
    val imageId: Int

)

class InvalidMedicineException(message: String) : Exception(message)