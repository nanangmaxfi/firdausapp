package id.web.nanangmaxfi.firdausapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class JadwalSholatDailyResponse(

	@field:SerializedName("data")
	val data: Data = Data(),

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class Data(

	@field:SerializedName("jadwal")
	val jadwal: Jadwal = Jadwal(),

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("daerah")
	val daerah: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("koordinat")
	val koordinat: Koordinat? = null
)

data class Jadwal(

	@field:SerializedName("date")
	val date: String = "",

	@field:SerializedName("imsak")
	val imsak: String = "",

	@field:SerializedName("isya")
	val isya: String = "",

	@field:SerializedName("dzuhur")
	val dzuhur: String = "",

	@field:SerializedName("subuh")
	val subuh: String = "",

	@field:SerializedName("dhuha")
	val dhuha: String = "",

	@field:SerializedName("terbit")
	val terbit: String = "",

	@field:SerializedName("tanggal")
	val tanggal: String = "",

	@field:SerializedName("ashar")
	val ashar: String = "",

	@field:SerializedName("maghrib")
	val maghrib: String = ""
)

data class Koordinat(

	@field:SerializedName("lintang")
	val lintang: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("bujur")
	val bujur: String? = null
)
