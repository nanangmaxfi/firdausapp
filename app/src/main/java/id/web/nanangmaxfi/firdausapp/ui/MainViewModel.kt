package id.web.nanangmaxfi.firdausapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.web.nanangmaxfi.firdausapp.data.source.PrayerScheduleRepository
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.remote.RemoteDataSource

class MainViewModel : ViewModel() {
    private val remoteDataSource = RemoteDataSource.getInstance()
    private val prayerScheduleRepository: PrayerScheduleRepository = PrayerScheduleRepository.getInstance(remoteDataSource)
    companion object{
        private var TAG = this::class.java.simpleName
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getPrayerSchedule(city: String, date: String) : LiveData<JadwalSholatEntity> =
        prayerScheduleRepository.getPrayerSchedule(city, date)
}