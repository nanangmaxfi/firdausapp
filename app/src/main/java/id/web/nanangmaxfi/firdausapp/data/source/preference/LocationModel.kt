package id.web.nanangmaxfi.firdausapp.data.source.preference

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationModel(
    var cityCode: String? = null,
    var name: String? = null
) : Parcelable
