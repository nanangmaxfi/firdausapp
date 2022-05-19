package id.web.nanangmaxfi.firdausapp.network_utils

import id.web.nanangmaxfi.firdausapp.data.source.remote.response.JadwalSholatResponse
import id.web.nanangmaxfi.firdausapp.data.source.remote.response.LocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("sholat/jadwal/{city}/{year}/{month}/{date}")
    fun getPrayerSchedule(
        @Path("city") city: String,
        @Path("year") year: String,
        @Path("month") month: String,
        @Path("date") date: String
    ): Call<JadwalSholatResponse>

    @GET("sholat/kota/cari/{city}")
    fun getSearchLocation(@Path("city") city: String) : Call<LocationResponse>
}