package com.dicoding.recyclerviewapp

import android.os.Parcelable
//import android.service.autofill.FillEventHistory
import kotlinx.parcelize.Parcelize


@Parcelize
data class aircraft (

    val name: String,
    val description: String,
    val history: String,
    val meaningOfLogo: String,
    val aircraftType: String,
    val photo: Int,
    val logo: Int




) : Parcelable