package id.web.nanangmaxfi.firdausapp.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.remote.RemoteDataSource
import id.web.nanangmaxfi.firdausapp.data.source.remote.response.JadwalSholatResponse

class PrayerScheduleRepository private constructor(private val remoteDataSource: RemoteDataSource): PrayerScheduleDataSource {
    companion object{
        private val TAG: String = PrayerScheduleRepository::class.java.simpleName
        @Volatile
        private var instance: PrayerScheduleRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): PrayerScheduleRepository =
            instance ?: synchronized(this){
                instance ?: PrayerScheduleRepository(remoteDataSource)
            }
    }
    override fun getPrayerSchedule(city: String, date: String): LiveData<JadwalSholatEntity> {
        val result = MutableLiveData<JadwalSholatEntity>()
        remoteDataSource.getPrayerSchedule(city, date, object: RemoteDataSource.LoadScheduleCallback{
            override fun onDataReceived(jadwalSholatResponse: JadwalSholatResponse?) {
                val jadwalSholatEntity = if ("ok" == jadwalSholatResponse?.status){
                    val data = jadwalSholatResponse.jadwal.data
                    JadwalSholatEntity(
                        data.imsak, data.isya, data.dzuhur, data.dhuha, data.subuh, data.terbit,
                        data.ashar, data.tanggal, data.maghrib
                    )
                }else{
                    val data = jadwalSholatResponse?.jadwal?.data
                    JadwalSholatEntity(
                        data?.imsak, data?.isya, data?.dzuhur, data?.dhuha, data?.subuh, data?.terbit,
                        data?.ashar, data?.tanggal, data?.maghrib)
                }
                result.postValue(jadwalSholatEntity)
            }
        })
        return result
    }
}