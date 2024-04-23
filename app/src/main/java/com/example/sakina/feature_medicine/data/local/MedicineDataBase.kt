package com.example.sakina.feature_medicine.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sakina.feature_medicine.domain.model.Medicine


@Database(
    entities = [Medicine::class], version = 2
)
@TypeConverters(Converters::class)
abstract class MedicineDataBase : RoomDatabase() {

    abstract fun getDao(): MedicineDao

    companion object {
        @Volatile
        private var instance: MedicineDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context): MedicineDataBase {
            return Room.databaseBuilder(
                context.applicationContext, MedicineDataBase::class.java, "notes_db.db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}