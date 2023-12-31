package com.revakovskyi.giphy.presentation.screens.gifs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.domain.useCases.GetSearchedGifsUseCase
import com.revakovskyi.domain.useCases.GetTrendingGifsUseCase
import com.revakovskyi.domain.util.DataResult
import com.revakovskyi.giphy.core.QueryManager
import com.revakovskyi.giphy.presentation.screens.gifs.model.GifsEvent
import com.revakovskyi.giphy.presentation.screens.gifs.model.GifsState
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getTrendingGifs(shouldRefreshGifs = false)
        }
    }

    fun onEvent(event: GifsEvent) {
        when (event) {
            is GifsEvent.ProvideGifsByQuery -> getSearchedGifs(event.query)
            GifsEvent.RefreshGifs -> refreshGifs()
            is GifsEvent.OnGifClick -> state = state.copy(chosenGifUrl = event.gifUrl)
            GifsEvent.OnBackButtonPressed -> chooseAction()
            GifsEvent.ResetChosenGifUrl -> state = state.copy(chosenGifUrl = "")
            GifsEvent.ResetGifUrls -> state = state.copy(gifsUrls = null, errorMessage = "")
        }
    }

    private fun getTrendingGifs(shouldRefreshGifs: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                state = state.copy(
                    gifsUrls = if (shouldRefreshGifs) null else if (state.gifsUrls?.isNotEmpty() == true) state.gifsUrls else null,
                    isLoading = true,
                    enteredQuery = "",
                    errorMessage = ""
                )
            }
            val dataResult = getTrendingGifsUseCase(shouldRefreshGifs)
            processDataResult(dataResult)
        }
    }

    private fun getSearchedGifs(enteredQuery: String) {
        val query = checkQueryForEmptinessAndGiveItBack(enteredQuery)

        if (query.isEmpty()) showTrendingGifs()
        else {
            val status = verifyQueryForCorrectSpelling(query)
            if (status == QueryManager.Status.Correct) startSearchingGifs(query)
        }
    }

    private fun refreshGifs() {
        if (state.enteredQuery.isNotEmpty()) getSearchedGifs(state.enteredQuery)
        else getTrendingGifs(shouldRefreshGifs = true)
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
        state = state.copy(
            queryVerificationStatus = status,
            errorMessage = "",
            gifsUrls = if (state.gifsUrls?.isEmpty() == true) null else state.gifsUrls
        )
        return status
    }

    private fun startSearchingGifs(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500L)
            withContext(Dispatchers.Main) {
                state = state.copy(isLoading = true, errorMessage = "")
            }
            val dataResult = getSearchedGifsUseCase(query)
            processDataResult(dataResult)
        }
    }

    private suspend fun processDataResult(dataResult: DataResult<List<String>>) {
        withContext(Dispatchers.Main) {
            state = when (dataResult) {

                is DataResult.Success -> {
                    val gifsUrls = dataResult.data
                    state.copy(gifsUrls = gifsUrls, isLoading = false)
                }

                is DataResult.Error -> {
                    val errorMessage = dataResult.message.toString()
                    state.copy(errorMessage = errorMessage, isLoading = false)
                }

            }
        }
    }

    private fun chooseAction() {
        if (state.enteredQuery.isNotEmpty()) {
            state = state.copy(
                enteredQuery = "",
                queryVerificationStatus = QueryManager.Status.Neutral
            )
            getTrendingGifs(shouldRefreshGifs = false)
        } else state = state.copy(shouldCloseTheApp = true)
    }

}
