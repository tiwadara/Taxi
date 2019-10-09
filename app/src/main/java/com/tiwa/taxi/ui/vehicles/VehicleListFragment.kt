package com.tiwa.taxi.ui.vehicles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiwa.taxi.R
import com.tiwa.taxi.data.model.Poi
import com.tiwa.taxi.data.viewmodel.VehicleViewModel
import kotlinx.android.synthetic.main.fragment_vehicle.*


class VehicleListFragment : Fragment() {

    private lateinit var viewModel: VehicleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vehicle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewElements()
        observerViewModels()
    }


    private fun prepareViewElements() {
        vehiclesRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun observerViewModels() {

        viewModel = ViewModelProviders.of(this).get(VehicleViewModel::class.java)

        viewModel.getPoi().observe(this,
            Observer<List<Poi>> { poisList ->
                if (poisList != null) {
                    vehiclesRecyclerView.adapter = VehicleAdapter(context, poisList) { vehicleSelected(it) }

                }
            })
    }

    private fun vehicleSelected(poi: Poi) {
        viewModel.setSelected(poi)
        val action = VehicleListFragmentDirections.actionNavigationVehiclesToNavigationMap()
        action.zoomIntoOne = true
        view?.findNavController()?.navigate( action)
    }
}
