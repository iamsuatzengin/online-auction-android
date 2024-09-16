package com.suatzengin.onlineauction.data

import com.suatzengin.onlineauction.data.KtorClient.HTTP_URL
import com.suatzengin.onlineauction.data.model.Auction
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuctionRepository {
    private val client = KtorClient.client

    suspend fun getAuctions(): List<Auction> = withContext(Dispatchers.IO) {
        val response = client.get(urlString = HTTP_URL) {
            url {
                appendPathSegments("auctions")
            }
        }

        return@withContext response.body()
    }
}