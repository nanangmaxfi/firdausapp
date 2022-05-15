package id.web.nanangmaxfi.firdausapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity

@Database(entities = [JadwalSholatEntity::class, LocationEntity::class], version = 1, exportSchema = false)
abstract class FirdausDatabase : RoomDatabase() {
    abstract fun prayerScheduleDao() : PrayerScheduleDao

    companion object{
        @Volatile
        private var INSTANCE : FirdausDatabase? = null

        fun getInstance(context: Context): FirdausDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                FirdausDatabase::class.java,
                "Firdaus.db").build()
            }
    }
}