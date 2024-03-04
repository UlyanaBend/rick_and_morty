package com.example.rick_and_morty.app

import android.app.Application
import com.example.rick_and_morty.di.AppComponent
import com.example.rick_and_morty.di.DaggerAppComponent
import com.example.rick_and_morty.di.DataModule
import com.example.rick_and_morty.di.DomainModule
import com.example.rick_and_morty.di.PresentationModule

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .presentationModule(PresentationModule(context = this))
            .dataModule(DataModule(context = this))
            .domainModule(DomainModule())
            .build()
    }
}