package id.web.nanangmaxfi.firdausapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import id.web.nanangmaxfi.firdausapp.R
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.data.source.preference.LocationModel
import id.web.nanangmaxfi.firdausapp.data.source.preference.LocationPreference
import id.web.nanangmaxfi.firdausapp.databinding.ActivityMainBinding
import id.web.nanangmaxfi.firdausapp.utils.ConfigUtils
import id.web.nanangmaxfi.firdausapp.viewmodel.ViewModelFactory
import id.web.nanangmaxfi.firdausapp.vo.Status

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val configUtils = ConfigUtils.getInstance()
    private lateinit var locationPreference: LocationPreference
    private lateinit var locationModel: LocationModel

    companion object {
        private val TAG: String = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        supportActionBar?.hide()

        locationPreference = LocationPreference(this)
        locationModel = locationPreference.getLocation()
        if (locationModel.cityCode.isNullOrBlank()){
            locationModel.cityCode = "1501"
            locationModel.name = "KAB. BANTUL"

            locationPreference.setLocation(locationModel)
        }

        binding.textCity.text = locationModel.name
        Log.i(TAG,"INFO DATE: "+configUtils.getCurrentDate())
        viewModel.getPrayerSchedule(locationModel.cityCode!!,configUtils.getCurrentYear(),
            configUtils.getCurrentMonth(), configUtils.getCurrentDateNumb(),"today").observe(this) { jadwal ->
            if (jadwal != null) {
                when (jadwal.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        showPrayerSchedule(jadwal.data)
                        checkNextSchedule(jadwal.data)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        binding.toolbarMain.imageMap.setOnClickListener {
            val bottomSheetLocation = BottomSheetLocation()
            bottomSheetLocation.show(supportFragmentManager,"BottomSheetLocation")
        }
    }

    private fun showPrayerSchedule(data: JadwalSholatEntity?){
        binding.textDatetime.text = data?.tanggal
        binding.textTimeSubuh.text = data?.subuh
        binding.textTimeFajar.text = data?.terbit
        binding.textTimeDzuhur.text = data?.dzuhur
        binding.textTimeAshar.text = data?.ashar
        binding.textTimeMaghrib.text = data?.maghrib
        binding.textTimeIsya.text = data?.isya
    }

    private fun checkNextSchedule(data: JadwalSholatEntity?){
        val currentTime = System.currentTimeMillis()

        if (currentTime <= configUtils.convertTimeToMilis(data?.subuh)){
            showNextSchedule(getString(R.string.subuh), data?.subuh)
            countdonw(configUtils.diffTimeLong(configUtils.convertTimeToMilis(data?.subuh)))
        }
        else if (currentTime > configUtils.convertTimeToMilis(data?.subuh) &&
                currentTime <= configUtils.convertTimeToMilis(data?.dzuhur)){
            showNextSchedule(getString(R.string.dzuhur), data?.dzuhur)
            countdonw(configUtils.diffTimeLong(configUtils.convertTimeToMilis(data?.dzuhur)))
        }
        else if (currentTime > configUtils.convertTimeToMilis(data?.dzuhur) &&
                currentTime <= configUtils.convertTimeToMilis(data?.ashar)){
            showNextSchedule(getString(R.string.ashar), data?.ashar)
            countdonw(configUtils.diffTimeLong(configUtils.convertTimeToMilis(data?.ashar)))
        }
        else if (currentTime > configUtils.convertTimeToMilis(data?.ashar) &&
                currentTime <= configUtils.convertTimeToMilis(data?.maghrib)){
            showNextSchedule(getString(R.string.maghrib), data?.maghrib)
            countdonw(configUtils.diffTimeLong(configUtils.convertTimeToMilis(data?.maghrib)))
        }
        else if (currentTime > configUtils.convertTimeToMilis(data?.maghrib) &&
                currentTime <= configUtils.convertTimeToMilis(data?.isya)){
            showNextSchedule(getString(R.string.isya), data?.isya)
            countdonw(configUtils.diffTimeLong(configUtils.convertTimeToMilis(data?.isya)))
        }
        else if (currentTime > configUtils.convertTimeToMilis(data?.isya)){
            forTomorrow()
        }
    }

    private fun forTomorrow(){
        viewModel.getPrayerSchedule(locationModel.cityCode!!,configUtils.getTomorrowDate("yyyy"),
            configUtils.getTomorrowDate("MM"), configUtils.getTomorrowDate("dd"), "tomorrow").observe(this) { jadwal ->
            if (jadwal != null) {
                when (jadwal.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        showNextSchedule(getString(R.string.subuh), jadwal.data?.subuh)
                        countdonw(
                            configUtils.diffTimeLong(
                                configUtils.convertTimeToMilisByDate(
                                    configUtils.getTomorrowDate(),
                                    jadwal.data?.subuh
                                )
                            )
                        )
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showNextSchedule(name: String, time: String?){
        binding.textSholat.text = name
        binding.textTime.text = time
    }

    private fun countdonw(timeMilis: Long){
        val countdown = object: CountDownTimer(timeMilis, 1000){
            override fun onTick(millisUntilFinished: Long) {
                val hour = ((millisUntilFinished / 1000) / 60)/60
                val minute = ((millisUntilFinished / 1000) / 60) % 60
                val seconds = (millisUntilFinished / 1000) % 60

                binding.textCountdown.text ="$hour jam $minute menit $seconds detik"
            }

            override fun onFinish() {

            }
        }

        countdown.start()
    }
}