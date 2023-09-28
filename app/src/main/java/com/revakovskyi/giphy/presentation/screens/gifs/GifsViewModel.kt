package com.revakovskyi.giphy.presentation.screens.gifs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.domain.models.Gif
import com.revakovskyi.domain.useCases.GetSearchedGifsUseCase
import com.revakovskyi.domain.useCases.GetTrendingGifsUseCase
import com.revakovskyi.domain.util.DataResult
import com.revakovskyi.giphy.presentation.models.mapToGifUi
import com.revakovskyi.giphy.presentation.screens.gifs.mvi.GifsEvent
import com.revakovskyi.giphy.presentation.screens.gifs.mvi.GifsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GifsViewModel @Inject constructor(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
    private val getSearchedGifsUseCase: GetSearchedGifsUseCase,
) : ViewModel() {

    var state by mutableStateOf(GifsState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingGifs(shouldRefreshGifs = false)
        }
    }

    fun onEvent(event: GifsEvent) {
        viewModelScope.launch(Dispatchers.IO) {
            when (event) {
                is GifsEvent.ProvideGifsByQuery -> getSearchedGifs(event.query)
                GifsEvent.RefreshGifs -> getTrendingGifs(shouldRefreshGifs = true)
                GifsEvent.ResetState -> withContext(Dispatchers.Main) { state = GifsState() }
            }
        }
    }

    private suspend fun getTrendingGifs(shouldRefreshGifs: Boolean) {
        withContext(Dispatchers.Main) { state = state.copy(isLoading = true) }
        val dataResult = getTrendingGifsUseCase(shouldRefreshGifs)
        processDataResult(dataResult)
    }

    private suspend fun getSearchedGifs(query: String) {
        withContext(Dispatchers.Main) { state = state.copy(isLoading = true) }
        val dataResult = getSearchedGifsUseCase(query)
        processDataResult(dataResult)
    }

    private suspend fun processDataResult(dataResult: DataResult<List<Gif>>) {
        withContext(Dispatchers.Main) {
            state = when (dataResult) {

                is DataResult.Success -> {
                    val gifs = dataResult.data?.map { it.mapToGifUi() } ?: emptyList()
                    if (gifs.isNotEmpty()) state.copy(gifs = gifs, isLoading = false)
                    else state.copy(isLoading = false)
                }

                is DataResult.Error -> {
                    val errorMessage = dataResult.message.toString()
                    state.copy(errorMessage = errorMessage, isLoading = false)
                }

            }
        }
    }

}
