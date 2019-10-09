package com.tiwa.taxi.di

import dagger.Component

@Component
interface VehiclesComponent {

    interface Builder {
        fun build(): VehiclesComponent
    }

    fun inject(app: App)
}