package id.web.nanangmaxfi.firdausapp.data.source.local

import androidx.lifecycle.LiveData
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.local.room.PrayerScheduleDao

class LocalDataSource private constructor(private val prayerScheduleDao: PrayerScheduleDao){
    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(prayerScheduleDao: PrayerScheduleDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(prayerScheduleDao)
    }

    fun getAllPrayerSchedules() : LiveData<List<JadwalSholatEntity>> = prayerScheduleDao.getAllSchedules()
    fun getCityPrayerSchedule(cityCode:String) : LiveData<JadwalSholatEntity> = prayerScheduleDao.getCitySchedule(cityCode)
    fun insertPrayerSchedule(prayerSchedule: JadwalSholatEntity) = prayerScheduleDao.insertSchedule(prayerSchedule)
    fun updatePrayerSchedule(prayerSchedule: JadwalSholatEntity) = prayerScheduleDao.updateSchedule(prayerSchedule)
}