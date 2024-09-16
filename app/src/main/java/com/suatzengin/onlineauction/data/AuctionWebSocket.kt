package com.suatzengin.onlineauction.data

import com.suatzengin.onlineauction.data.model.OfferRequestModel
import com.suatzengin.onlineauction.data.model.SocketApiModel
import com.suatzengin.onlineauction.data.model.SocketResult
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.close
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class AuctionWebSocket {
    private var socket: DefaultClientWebSocketSession? = null

    suspend fun connect(
        auctionId: Int,
    ): Flow<SocketResult> = flow<SocketResult> {
        socket = KtorClient.client.webSocketSession(
            urlString = KtorClient.SOCKET_URL + "auctions/${auctionId}"
        )

        val list = mutableListOf<SocketApiModel>()

        while (socket?.isActive == true) {

            val model = socket?.receiveDeserialized() ?: SocketApiModel()

            if(model.isOfferApproved == true) {
                list.add(model)
            }

            emit(
                SocketResult.Success(
                    offers = list,
                    bidderCount = model.bidderCount ?: 0,
                    currentPrice = model.currentPrice ?: 0,
                    isOfferApproved = model.isOfferApproved ?: false,
                    warningMessage = model.warningMessage ?: ""
                )
            )
        }
    }.flowOn(Dispatchers.IO)
        .catch { throwable ->
            when (throwable) {
                is ClosedReceiveChannelException -> {
                    emit(SocketResult.Error("Connection closed"))
                }
                else -> {
                    throwable.printStackTrace()
                }
            }

        }

    suspend fun sendMessage(offerRequestModel: OfferRequestModel) {
        runCatching {
            withContext(Dispatchers.IO) {
                socket?.sendSerialized(offerRequestModel)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

    suspend fun disconnect() {
        socket?.close()
        socket = null
    }
}
