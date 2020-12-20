package id.web.nanangmaxfi.firdausapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import id.web.nanangmaxfi.firdausapp.R
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.databinding.ActivityMainBinding
import id.web.nanangmaxfi.firdausapp.utils.ConfigUtils
import id.web.nanangmaxfi.firdausapp.viewmodel.ViewModelFactory
import id.web.nanangmaxfi.firdausapp.vo.Status
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding
    private val configUtils = ConfigUtils.getInstance()
    companion object {
        private val TAG: String = this::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        supportActionBar?.hide()
        activityMainBinding.textCity.text = "Bantul, Yogyakarta"
        viewModel.getPrayerSchedule("779",configUtils.getCurrentDate(), "today").observe(this, {jadwal ->
            if (jadwal != null){
                when(jadwal.status){
                    Status.LOADING ->{}
                    Status.SUCCESS ->{
                        showPrayerSchedule(jadwal.data)
                        checkNextSchedule(jadwal.data)
                    }
                    Status.ERROR ->{
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun showPrayerSchedule(data: JadwalSholatEntity?){
        activityMainBinding.textDatetime.text = data?.tanggal
        activityMainBinding.textTimeSubuh.text = data?.subuh
        activityMainBinding.textTimeFajar.text = data?.terbit
        activityMainBinding.textTimeDzuhur.text = data?.dzuhur
        activityMainBinding.textTimeAshar.text = data?.ashar
        activityMainBinding.textTimeMaghrib.text = data?.maghrib
        activityMainBinding.textTimeIsya.text = data?.isya
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
        viewModel.getPrayerSchedule("779",configUtils.getTomorrowDate(), "tomorrow").observe(this, {jadwal ->
            if (jadwal != null){
                when(jadwal.status){
                    Status.LOADING ->{}
                    Status.SUCCESS ->{
                        showNextSchedule(getString(R.string.subuh), jadwal.data?.subuh)
                        countdonw(configUtils.diffTimeLong(
                                configUtils.convertTimeToMilisByDate(configUtils.getTomorrowDate(), jadwal.data?.subuh)))
                    }
                    Status.ERROR ->{
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun showNextSchedule(name: String, time: String?){
        activityMainBinding.textSholat.text = name
        activityMainBinding.textTime.text = time
    }

    private fun countdonw(timeMilis: Long){
        val countdown = object: CountDownTimer(timeMilis, 1000){
            override fun onTick(millisUntilFinished: Long) {
                val hour = ((millisUntilFinished / 1000) / 60)/60
                val minute = ((millisUntilFinished / 1000) / 60) % 60
                val seconds = (millisUntilFinished / 1000) % 60

                activityMainBinding.textCountdown.text ="$hour jam $minute menit $seconds detik"
            }

            override fun onFinish() {

            }
        }

        countdown.start()
    }
}