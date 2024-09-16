package com.suatzengin.onlineauction.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OfferRequestModel(
    @SerialName("id")
    val id: Int,
    @SerialName("auctionId")
    val auctionId: Int,
    @SerialName("amount")
    val amount: Int,
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("bidderId")
    val bidderId: Int,
)
