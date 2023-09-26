package com.revakovskyi.giphy.presentation.screens.gifs.mvi

internal sealed class GifsEvent {

    data class ProvideGifs(val query: String) : GifsEvent()
    object RefreshGifs : GifsEvent()

}