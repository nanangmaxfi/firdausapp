package id.web.nanangmaxfi.firdausapp.data.source

import androidx.lifecycle.LiveData
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.vo.Resource

interface PrayerScheduleDataSource {
    fun getPrayerSchedule(city: String, date: String) : LiveData<Resource<JadwalSholatEntity>>
}