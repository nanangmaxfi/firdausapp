package id.web.nanangmaxfi.firdausapp.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import id.web.nanangmaxfi.firdausapp.data.source.local.LocalDataSource
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity
import id.web.nanangmaxfi.firdausapp.data.source.remote.ApiResponse
import id.web.nanangmaxfi.firdausapp.data.source.remote.RemoteDataSource
import id.web.nanangmaxfi.firdausapp.data.source.remote.response.JadwalSholatDailyResponse
import id.web.nanangmaxfi.firdausapp.data.source.remote.response.LocationResponse
import id.web.nanangmaxfi.firdausapp.utils.AppExecutors
import id.web.nanangmaxfi.firdausapp.vo.Resource

class PrayerScheduleRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
    )
    : PrayerScheduleDataSource {
    companion object{
        private val TAG: String = PrayerScheduleRepository::class.java.simpleName
        @Volatile
        private var instance: PrayerScheduleRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource,
        appExecutors: AppExecutors): PrayerScheduleRepository =
            instance ?: synchronized(this){
                instance ?: PrayerScheduleRepository(remoteDataSource, localDataSource, appExecutors)
            }
    }

    override fun getPrayerSchedule(
        city: String,
        year: String,
        month: String,
        date: String,
        session: String
    ): LiveData<Resource<JadwalSholatEntity>> {
        return object : NetworkBoundResource<JadwalSholatEntity, JadwalSholatDailyResponse>(appExecutors){
            override fun loadFromDB(): LiveData<JadwalSholatEntity> =
                localDataSource.getCityPrayerSchedule(city, session)

            override fun shouldFetch(data: JadwalSholatEntity?): Boolean =
                 data == null || data.date != date || data.cityCode != city

            override fun createCall(): LiveData<ApiResponse<JadwalSholatDailyResponse>> =
                remoteDataSource.getPrayerSchedule(city, year, month, date)


            override fun saveCallResult(data: JadwalSholatDailyResponse) {
                val jadwal = data.data.jadwal
                val jadwalSholatEntity = JadwalSholatEntity(session, city, date, jadwal.imsak, jadwal.isya, jadwal.dzuhur,
                                    jadwal.dhuha, jadwal.subuh, jadwal.terbit, jadwal.ashar, jadwal.tanggal, jadwal.maghrib)
                localDataSource.insertPrayerSchedule(jadwalSholatEntity)
            }

        }.asLiveData()
    }

    override fun getLocation(city: String): LiveData<Resource<List<LocationEntity>>> {
       return object : NetworkBoundResource<List<LocationEntity>, LocationResponse>(appExecutors){
           override fun loadFromDB(): LiveData<List<LocationEntity>> {
               return localDataSource.getLocation(city)
           }

           override fun shouldFetch(data: List<LocationEntity>?): Boolean {
               return data == null || data.isEmpty()
           }

           override fun createCall(): LiveData<ApiResponse<LocationResponse>> =
               remoteDataSource.getSearchLocation(city)

           override fun saveCallResult(data: LocationResponse) {
               val locationList = data.data
               if (locationList != null) {
                   for(location in locationList){
                        val locationEnity = LocationEntity(location.id, location.lokasi)
                       localDataSource.insertLocation(locationEnity)
                   }
               }
           }

       }.asLiveData()
    }
//    override fun getPrayerSchedule(city: String, date: String): LiveData<JadwalSholatEntity> {
//        val result = MutableLiveData<JadwalSholatEntity>()
//        remoteDataSource.getPrayerSchedule(city, date, object: RemoteDataSource.LoadScheduleCallback{
//            override fun onDataReceived(jadwalSholatResponse: JadwalSholatResponse?) {
//                val jadwalSholatEntity = if ("ok" == jadwalSholatResponse?.status){
//                    val data = jadwalSholatResponse.jadwal.data
//                    JadwalSholatEntity(
//                        data.imsak, data.isya, data.dzuhur, data.dhuha, data.subuh, data.terbit,
//                        data.ashar, data.tanggal, data.maghrib
//                    )
//                }else{
//                    val data = jadwalSholatResponse?.jadwal?.data
//                    JadwalSholatEntity(
//                        data?.imsak, data?.isya, data?.dzuhur, data?.dhuha, data?.subuh, data?.terbit,
//                        data?.ashar, data?.tanggal, data?.maghrib)
//                }
//                result.postValue(jadwalSholatEntity)
//            }
//        })
//        return result
//    }
}