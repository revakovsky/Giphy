package com.revakovskyi.giphy.presentation.screens.gifs.mvi

sealed class GifsEvent {

    data class ProvideGifsByQuery(val query: String = "") : GifsEvent()
    object RefreshGifs : GifsEvent()
    data class OnGifClick(val gifUrl: String = "") : GifsEvent()
    object OnBackButtonPressed : GifsEvent()
    object ResetChosenGifUrl : GifsEvent()
    object ResetState : GifsEvent()

}
