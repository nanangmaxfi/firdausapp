package id.web.nanangmaxfi.firdausapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.web.nanangmaxfi.firdausapp.data.source.PrayerScheduleRepository
import id.web.nanangmaxfi.firdausapp.di.Injection
import id.web.nanangmaxfi.firdausapp.ui.MainViewModel

class ViewModelFactory private constructor(private val prayerScheduleRepository: PrayerScheduleRepository)
    : ViewModelProvider.NewInstanceFactory(){
        companion object{
            @Volatile
            private var instance : ViewModelFactory? = null

            fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this){
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(prayerScheduleRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}