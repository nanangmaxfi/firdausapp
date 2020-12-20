package id.web.nanangmaxfi.firdausapp.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jadwalsholatentities")
data class JadwalSholatEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "session")
    var session: String,

    @ColumnInfo(name = "cityCode")
    var cityCode: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "imsak")
    var imsak: String,

    @ColumnInfo(name = "isya")
    var isya: String,

    @ColumnInfo(name = "dzuhur")
    var dzuhur: String,

    @ColumnInfo(name = "dhuha")
    var dhuha: String,

    @ColumnInfo(name = "subuh")
    var subuh: String,

    @ColumnInfo(name = "terbit")
    var terbit: String,

    @ColumnInfo(name = "ashar")
    var ashar: String,

    @ColumnInfo(name = "tanggal")
    var tanggal: String,

    @ColumnInfo(name = "maghrib")
    val maghrib: String
)
