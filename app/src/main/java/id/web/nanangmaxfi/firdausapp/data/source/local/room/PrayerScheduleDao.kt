package id.web.nanangmaxfi.firdausapp.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity

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

    @Query("SELECT * FROM locationentities WHERE name LIKE '%' || :name || '%'")
    fun getListLocation(name: String) : LiveData<List<LocationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: LocationEntity)
}