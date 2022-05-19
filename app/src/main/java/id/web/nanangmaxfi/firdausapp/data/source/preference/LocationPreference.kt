package id.web.nanangmaxfi.firdausapp.data.source.preference

import android.content.Context

class LocationPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "location_pref"
        private const val CITY_CODE = "city_code"
        private const val NAME = "name"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLocation(value: LocationModel){
        val editor = preferences.edit()
        editor.putString(CITY_CODE, value.cityCode)
        editor.putString(NAME, value.name)
        editor.apply()
    }

    fun getLocation(): LocationModel {
        val model = LocationModel()
        model.cityCode = preferences.getString(CITY_CODE, "")
        model.name = preferences.getString(NAME, "")
        return  model
    }
}