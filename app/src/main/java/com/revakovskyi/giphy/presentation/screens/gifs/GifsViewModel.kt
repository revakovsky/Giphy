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
import com.revakovskyi.giphy.core.QueryManager
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
    private val queryManager: QueryManager,
) : ViewModel() {

    private var searchJob: Job? = null
    var state by mutableStateOf(GifsState())
        private set

    private var query = ""

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingGifs(shouldRefreshGifs = false)
        }
    }

    fun onEvent(event: GifsEvent) {
        when (event) {
            is GifsEvent.ProvideGifsByQuery -> getSearchedGifs(event.query)
            GifsEvent.RefreshGifs -> getTrendingGifs(shouldRefreshGifs = true)
            GifsEvent.OnBackButtonPressed -> chooseAction()
            GifsEvent.ResetState -> state = GifsState()
        }
    }

    private fun getTrendingGifs(shouldRefreshGifs: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                query = ""
                state = state.copy(gifs = emptyList(), isLoading = true, enteredQuery = query)
            }
            val dataResult = getTrendingGifsUseCase(shouldRefreshGifs)
            processDataResult(dataResult)
        }
    }

    private fun getSearchedGifs(enteredQuery: String) {
        query = checkQueryForEmptinessAndGiveItBack(enteredQuery)

        if (query.isEmpty()) showTrendingGifs()
        else {
            val status = verifyQueryForCorrectSpelling(query)
            if (status == QueryManager.Status.Correct) startSearchingGifs(query)
        }
    }

    private fun checkQueryForEmptinessAndGiveItBack(enteredQuery: String): String {
        val query = if (enteredQuery.startsWith(" ") || enteredQuery.isEmpty()) ""
        else enteredQuery
        state = state.copy(enteredQuery = query)
        return query
    }

    private fun showTrendingGifs() {
        state = state.copy(queryVerificationStatus = QueryManager.Status.Neutral)
        getTrendingGifs(shouldRefreshGifs = false)
    }

    private fun verifyQueryForCorrectSpelling(query: String): QueryManager.Status {
        val status = queryManager.verifyQuery(query)
        state = state.copy(queryVerificationStatus = status)
        return status
    }

    private fun startSearchingGifs(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500L)
            withContext(Dispatchers.Main) { state = state.copy(isLoading = true) }
            val dataResult = getSearchedGifsUseCase(query)
            processDataResult(dataResult)
        }
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

    private fun chooseAction() {
        if (query.isNotEmpty()) {
            query = ""
            state = state.copy(
                enteredQuery = query,
                queryVerificationStatus = QueryManager.Status.Neutral
            )
            getTrendingGifs(shouldRefreshGifs = false)
        } else state = state.copy(shouldCloseTheApp = true)
    }

}
