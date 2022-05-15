package id.web.nanangmaxfi.firdausapp.ui.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.web.nanangmaxfi.firdausapp.data.source.PrayerScheduleRepository
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity
import id.web.nanangmaxfi.firdausapp.vo.Resource

class LocationViewModel(private val prayerScheduleRepository: PrayerScheduleRepository) : ViewModel() {
    companion object{
        private var TAG = this::class.java.simpleName
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSearchLocation(city: String) : LiveData<Resource<List<LocationEntity>>> =
        prayerScheduleRepository.getLocation(city)
}