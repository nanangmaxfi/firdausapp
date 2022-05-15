package id.web.nanangmaxfi.firdausapp.ui.location

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import id.web.nanangmaxfi.firdausapp.R
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity
import id.web.nanangmaxfi.firdausapp.databinding.ItemLocationBinding

class LocationItem(private val location: LocationEntity) : BindableItem<ItemLocationBinding>(){

    override fun bind(viewBinding: ItemLocationBinding, position: Int) {
        viewBinding.textCity.text = location.name
    }

    override fun getLayout(): Int =
        R.layout.item_location

    override fun initializeViewBinding(view: View): ItemLocationBinding =
        ItemLocationBinding.bind(view)
}