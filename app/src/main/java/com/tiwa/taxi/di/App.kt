package com.tiwa.taxi.di

import android.app.Application
import dagger.Component


//@Component
class App : Application() {


    override fun onCreate() {
        super.onCreate()
//        configureDagger()
    }

    private fun configureDagger() {
//        VehiclesComponent.builder()
//            .build()
//            .inject(this)
    }
}