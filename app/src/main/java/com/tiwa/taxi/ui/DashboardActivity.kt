package com.tiwa.taxi.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tiwa.taxi.R
import com.tiwa.taxi.data.viewmodel.VehicleViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    private lateinit var vehicleViewModel: VehicleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        toolbar.title = getString(R.string.title)
        toolbar.setLogo(R.mipmap.ic_taxi)
        toolbar.setTitleTextColor(ResourcesCompat.getColor(resources,R.color.colorWhite, null))

        observerViewModels()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_content) as NavHostFragment?
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment!!.navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            onNavDestinationSelected(item, findNavController(this, R.id.main_content))
        }



    }

    private fun observerViewModels() {
        vehicleViewModel = ViewModelProviders.of(this).get(VehicleViewModel::class.java)
        vehicleViewModel.getIsLoading().observe(this, Observer<Boolean> { isLoading ->
            if (isLoading) {
                toolbarprogress.visibility = View.VISIBLE
            } else {
                toolbarprogress.visibility = View.INVISIBLE
            }
        })
    }

}
