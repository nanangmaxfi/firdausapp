package id.web.nanangmaxfi.firdausapp.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class DataItem(

	@field:SerializedName("lokasi")
	val lokasi: String = "",

	@field:SerializedName("id")
	val id: String = ""
)
