package com.suatzengin.onlineauction.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Auction(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("startPrice")
    val startPrice: Int,
    @SerialName("currentPrice")
    val currentPrice: Int,
    @SerialName("startTime")
    val startTime: String,
    @SerialName("endTime")
    val endTime: String,
) : Parcelable
