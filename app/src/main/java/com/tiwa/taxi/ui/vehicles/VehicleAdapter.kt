package com.tiwa.taxi.ui.vehicles

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tiwa.taxi.R
import com.tiwa.taxi.data.model.Poi


class VehicleAdapter(
    val context: Context?,
    private val poiList: List<Poi>,
    private val itemClicked: (poi: Poi) -> Unit
) : RecyclerView.Adapter<VehicleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_vehicles, parent, false)
        return VehicleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {

        val poi = poiList[position]
        holder.bindItems(context, poi)
        holder.itemView.setOnClickListener {
            itemClicked.invoke(poi)
        }

    }
    override fun getItemCount(): Int {
        return poiList.size
    }

}