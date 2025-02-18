package com.assignment.android.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AssignmentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize other things if needed
    }
}