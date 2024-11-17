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
//    val country: String,
//    val founded:String,
//    val startOperating:String,
//    val owner: String,
//    val aircraft: Int,
    val photo: Int,
    val logo: Int




) : Parcelable