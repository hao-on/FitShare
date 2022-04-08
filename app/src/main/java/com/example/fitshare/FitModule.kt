package com.example.fitshare


import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var fitApp: App
//const val PARTITION_EXTRA_KEY = "PARTITION"

class FitModule : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the Realm SDK
        Realm.init(this)
        fitApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .defaultSyncErrorHandler { session, error ->
                    Log.e("Config", "Sync error: ${error.errorMessage}")
                }
                .build())

        // Enable more logging in debug mode
        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }

        Log.v("Config", "Initialized the Realm App configuration for: ${fitApp.configuration.appId}")
    }
}