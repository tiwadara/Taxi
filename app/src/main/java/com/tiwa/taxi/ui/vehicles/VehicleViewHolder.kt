package com.tiwa.taxi.ui.vehicles

import android.content.Context
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.tiwa.taxi.R
import com.tiwa.taxi.data.constant.Constants.POOLING
import com.tiwa.taxi.data.constant.Constants.TAXI
import com.tiwa.taxi.data.model.Poi
import kotlinx.android.synthetic.main.list_item_vehicles.view.*

open class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bindItems(context: Context?, poiList: Poi) {
        itemView.textViewFleetType.text = poiList.fleetType
        itemView.textViewVehicleLocation.text = poiList.address
        itemView.textViewPoiNumber.text = poiList.id.toString()

        when {
            poiList.fleetType == POOLING -> {
                itemView.cardViewVehicle.setCardBackgroundColor(
                            ResourcesCompat.getColor(
                                context?.resources!!,
                                R.color.colorPrimary,
                                null
                            )
                )
                itemView.imageViewFleetType.setImageResource(R.mipmap.ic_pool)
            }
            poiList.fleetType == TAXI -> {
                itemView.cardViewVehicle.setCardBackgroundColor(
                    ResourcesCompat.getColor(
                        context?.resources!!,
                        R.color.colorAccent,
                        null
                    )
                )
                itemView.imageViewFleetType.setImageResource(R.mipmap.ic_taxi)
            }
        }

    }

}
