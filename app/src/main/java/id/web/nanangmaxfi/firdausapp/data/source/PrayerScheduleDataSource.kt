package id.web.nanangmaxfi.firdausapp.data.source

import androidx.lifecycle.LiveData
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity
import id.web.nanangmaxfi.firdausapp.vo.Resource

interface PrayerScheduleDataSource {
    fun getPrayerSchedule(city: String, year: String, month: String, date: String, session: String) : LiveData<Resource<JadwalSholatEntity>>
    fun getLocation(city: String) : LiveData<Resource<List<LocationEntity>>>
}