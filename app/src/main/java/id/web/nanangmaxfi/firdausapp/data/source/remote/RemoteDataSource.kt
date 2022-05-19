package id.web.nanangmaxfi.firdausapp.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.web.nanangmaxfi.firdausapp.data.source.remote.response.JadwalSholatResponse
import id.web.nanangmaxfi.firdausapp.data.source.remote.response.LocationResponse
import id.web.nanangmaxfi.firdausapp.network_utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {
    companion object{
        private val TAG: String = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource()
            }
    }

    fun getPrayerSchedule(city: String, year: String, month: String, date: String) : LiveData<ApiResponse<JadwalSholatResponse>>{
        val resultSchedule = MutableLiveData<ApiResponse<JadwalSholatResponse>>()
        val client = ApiConfig.getApiService().getPrayerSchedule(city, year, month, date)
        client.enqueue(object : Callback<JadwalSholatResponse> {
            override fun onResponse(
                call: Call<JadwalSholatResponse>,
                response: Response<JadwalSholatResponse>
            ) {
                if (response.isSuccessful){
                    val jadwalSholatResponse = response.body()
                    if (jadwalSholatResponse != null)
                        resultSchedule.value = ApiResponse.success(jadwalSholatResponse)
                    else
                        resultSchedule.value = ApiResponse.error("Data kosong", JadwalSholatResponse())

                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<JadwalSholatResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
        return resultSchedule
    }

    fun getSearchLocation(city: String) : LiveData<ApiResponse<LocationResponse>>{
        Log.i(TAG, "Search Location ")
        val resultLocation = MutableLiveData<ApiResponse<LocationResponse>>()
        val client = ApiConfig.getApiService().getSearchLocation(city)
        client.enqueue(object : Callback<LocationResponse>{
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if (response.isSuccessful){
                    val locationResponse = response.body()
                    if (locationResponse != null){
                        resultLocation.value = ApiResponse.success(locationResponse)
                    }
                    else{
                        resultLocation.value = ApiResponse.error("Data Kosong", LocationResponse())
                    }
                }
                else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return resultLocation
    }

//    fun getPrayerSchedule(city: String, date: String, callback: LoadScheduleCallback){
//        val client = ApiConfig.getApiService().getPrayerSchedule(city, date)
//        client.enqueue(object : Callback<JadwalSholatResponse> {
//            override fun onResponse(
//                call: Call<JadwalSholatResponse>,
//                response: Response<JadwalSholatResponse>
//            ) {
//                if (response.isSuccessful){
//                    val jadwalSholatResponse = response.body()
//                    callback.onDataReceived(jadwalSholatResponse)
//                }
//                else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<JadwalSholatResponse>, t: Throwable) {
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

    interface LoadScheduleCallback{
        fun onDataReceived(jadwalSholatResponse: JadwalSholatResponse?)
    }

}