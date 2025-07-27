package com.example.teamez

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApp {
    class MyApp : Application() {
        override fun onCreate() {
            super.onCreate()
            FirebaseApp.initializeApp(this)
        }
    }

}