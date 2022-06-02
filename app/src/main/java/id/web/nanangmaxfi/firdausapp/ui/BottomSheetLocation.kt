package id.web.nanangmaxfi.firdausapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.web.nanangmaxfi.firdausapp.databinding.BottomSheetLocationBinding
import id.web.nanangmaxfi.firdausapp.ui.location.LocationActivity

class BottomSheetLocation : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetLocationBinding.inflate(inflater, container, false)
        return binding.root
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAutomaticLocation.setOnClickListener {
            Toast.makeText(view.context, "Lokasi Otomatis", Toast.LENGTH_LONG).show()
        }

        binding.btnManualLocation.setOnClickListener {
            val intent = Intent(view.context, LocationActivity::class.java)
            //startActivity(intent)
            startActivityForResult(intent, MainActivity.LAUNCH_LOCATION_ACTIVITY)
        }
    }
}