package id.web.nanangmaxfi.firdausapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class JadwalSholatResponse(

    @field:SerializedName("jadwal")
    val jadwal: Jadwal,

    @field:SerializedName("query")
    val query: Query,

    @field:SerializedName("status")
    val status: String
)

data class Data(

    @field:SerializedName("imsak")
    val imsak: String,

    @field:SerializedName("isya")
    val isya: String,

    @field:SerializedName("dzuhur")
    val dzuhur: String,

    @field:SerializedName("dhuha")
    val dhuha: String,

    @field:SerializedName("subuh")
    val subuh: String,

    @field:SerializedName("terbit")
    val terbit: String,

    @field:SerializedName("ashar")
    val ashar: String,

    @field:SerializedName("tanggal")
    val tanggal: String,

    @field:SerializedName("maghrib")
    val maghrib: String
)

data class Query(

    @field:SerializedName("kota")
    val kota: String,

    @field:SerializedName("format")
    val format: String,

    @field:SerializedName("tanggal")
    val tanggal: String
)

data class Jadwal(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("status")
    val status: String
)
