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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GifsViewModel @Inject constructor(
    private val getTrendingGifsUseCase: GetTrendingGifsUseCase,
    private val getSearchedGifsUseCase: GetSearchedGifsUseCase,
) : ViewModel() {

    private var searchJob: Job? = null
    var state by mutableStateOf(GifsState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingGifs(shouldRefreshGifs = true)
        }
    }

    fun onEvent(event: GifsEvent) {
        when (event) {
            is GifsEvent.ProvideGifsByQuery -> getSearchedGifs(event.query)
            GifsEvent.RefreshGifs -> getTrendingGifs(shouldRefreshGifs = true)
            GifsEvent.ResetState -> state = GifsState()
        }
    }

    private fun getTrendingGifs(shouldRefreshGifs: Boolean) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val dataResult = getTrendingGifsUseCase(shouldRefreshGifs)
            processDataResult(dataResult)
        }
    }

    private fun getSearchedGifs(query: String) {
        if (query.length > 1) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(500L)
                withContext(Dispatchers.Main) { state = state.copy(isLoading = true) }
                val dataResult = getSearchedGifsUseCase(query)
                processDataResult(dataResult)
            }
        }
        if (query.isEmpty()) getTrendingGifs(shouldRefreshGifs = false)
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
