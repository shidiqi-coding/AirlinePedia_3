//package com.dicoding.airlinepedia
//
//import android.os.Bundle
//import androidx.preference.ListPreference
//import androidx.preference.PreferenceFragmentCompat
//import androidx.preference.PreferenceManager
//import androidx.appcompat.app.AppCompatDelegate
//
//class SettingActivity : PreferenceFragmentCompat() {
//
//    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//        setPreferencesFromResource(R.xml.preferences, rootKey)
//
//        val themePreference = findPreference<ListPreference>("theme_preference")
//        themePreference?.setOnPreferenceChangeListener { _, newValue ->
//            when (newValue) {
//                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                "system" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//            }
//            true
//        }
//    }
//}