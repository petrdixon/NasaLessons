package com.example.nasalessons.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ModelRetrofitNasa(
    val date: String,
    val explanation: String,
    val hdurl: String,
    val title: String,
    val url: String,
): Parcelable

