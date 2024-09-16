package com.suatzengin.onlineauction.ui.auctiondetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suatzengin.onlineauction.data.AuctionWebSocket
import com.suatzengin.onlineauction.data.model.OfferRequestModel
import com.suatzengin.onlineauction.data.model.SocketApiModel
import com.suatzengin.onlineauction.data.model.SocketResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import kotlin.random.Random

class AuctionDetailViewModel(
    private val auctionWebSocket: AuctionWebSocket
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuctionDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun connect(auctionId: Int) {
        viewModelScope.launch {
            auctionWebSocket.connect(auctionId).collect { result ->
                when (result) {
                    is SocketResult.Success -> {
                        handleSuccessResult(
                            result.offers,
                            result.bidderCount,
                            result.currentPrice,
                            result.isOfferApproved,
                            result.warningMessage
                        )
                    }

                    is SocketResult.Error -> Unit
                }
            }
        }
    }

    private fun handleSuccessResult(
        offers: List<SocketApiModel>,
        bidderCount: Int,
        currentPrice: Int,
        offerApproved: Boolean,
        warningMessage: String
    ) = viewModelScope.launch {
        if(!offerApproved) {
            Log.i(
                "AuctionDetailViewModel",
                "Message = $warningMessage"
            )
            return@launch
        }

        _uiState.update { currentState ->
            currentState.copy(
                offers = offers,
                bidderCount = bidderCount,
                currentPrice = currentPrice
            )
        }
    }

    fun sendMessage(auctionId: Int, amount: Int) {
        viewModelScope.launch {
            val offerRequestModel = OfferRequestModel(
                id = Random.nextInt(),
                auctionId = auctionId,
                amount = amount,
                timestamp = Instant.now().toString(),
                bidderId = 0
            )

            auctionWebSocket.sendMessage(offerRequestModel)
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            auctionWebSocket.disconnect()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val auctionWebSocket = AuctionWebSocket()
                AuctionDetailViewModel(auctionWebSocket)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        disconnect()
    }
}
