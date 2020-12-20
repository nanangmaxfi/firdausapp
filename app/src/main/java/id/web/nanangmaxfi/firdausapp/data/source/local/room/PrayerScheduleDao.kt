package id.web.nanangmaxfi.firdausapp.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity

@Dao
interface PrayerScheduleDao {
    @Query("SELECT * FROM jadwalsholatentities")
    fun getAllSchedules() : LiveData<List<JadwalSholatEntity>>

    @Query("SELECT * FROM jadwalsholatentities WHERE cityCode = :cityCode AND session = :session")
    fun getCitySchedule(cityCode: String, session: String) : LiveData<JadwalSholatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(prayerSchedule: JadwalSholatEntity)

    @Update
    fun updateSchedule(prayerSchedule: JadwalSholatEntity)

}