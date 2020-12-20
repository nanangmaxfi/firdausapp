package id.web.nanangmaxfi.firdausapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.web.nanangmaxfi.firdausapp.data.source.PrayerScheduleRepository
import id.web.nanangmaxfi.firdausapp.data.source.local.LocalDataSource
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.local.room.FirdausDatabase
import id.web.nanangmaxfi.firdausapp.data.source.remote.RemoteDataSource
import id.web.nanangmaxfi.firdausapp.utils.AppExecutors
import id.web.nanangmaxfi.firdausapp.vo.Resource

class MainViewModel(private val prayerScheduleRepository: PrayerScheduleRepository) : ViewModel() {
    companion object{
        private var TAG = this::class.java.simpleName
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPrayerSchedule(city: String, date: String, session: String) : LiveData<Resource<JadwalSholatEntity>> =
        prayerScheduleRepository.getPrayerSchedule(city, date, session)
}