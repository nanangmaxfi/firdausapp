package id.web.nanangmaxfi.firdausapp.utils

import java.text.SimpleDateFormat
import java.util.*

class ConfigUtils {
    companion object{
        @Volatile
        private var instance: ConfigUtils? = null

        fun getInstance(): ConfigUtils =
                instance ?: synchronized(this){
                    instance ?: ConfigUtils()
                }
    }

    fun getCurrentDate(): String{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return format.format(Date())
    }

    fun convertTimeToMilis(time: String?): Long{
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val date = format.parse("${getCurrentDate()} $time")
        return date?.time?:Date().time
    }

    fun diffTimeLong(time: Long?): Long{
        val now = Date()
        return (time ?: now.time) - now.time
    }
}