package com.revakovskyi.giphy.presentation.screens.gifs.mvi

sealed class GifsEvent {

    data class ProvideGifsByQuery(val query: String = "") : GifsEvent()
    object RefreshGifs : GifsEvent()
    object OnBackButtonPressed : GifsEvent()
    object ResetState : GifsEvent()     // TODO: maybe delete

}
