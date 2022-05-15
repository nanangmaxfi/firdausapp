package id.web.nanangmaxfi.firdausapp.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationentities")
data class LocationEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "cityCode")
    var cityCode: String,

    @ColumnInfo(name = "name")
    var name: String
)
