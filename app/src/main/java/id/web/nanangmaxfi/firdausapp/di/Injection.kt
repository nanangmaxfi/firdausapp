package id.web.nanangmaxfi.firdausapp.di

import android.content.Context
import id.web.nanangmaxfi.firdausapp.data.source.PrayerScheduleRepository
import id.web.nanangmaxfi.firdausapp.data.source.local.LocalDataSource
import id.web.nanangmaxfi.firdausapp.data.source.local.room.FirdausDatabase
import id.web.nanangmaxfi.firdausapp.data.source.remote.RemoteDataSource
import id.web.nanangmaxfi.firdausapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): PrayerScheduleRepository{
        val database = FirdausDatabase.getInstance(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.prayerScheduleDao())
        val appExecutors = AppExecutors()

        return  PrayerScheduleRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}