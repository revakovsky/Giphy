package com.revakovskyi.domain.useCases

import com.revakovskyi.domain.repository.GifRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTrendingGifsUseCase @Inject constructor(
    private val gifRepository: GifRepository,
) {

    suspend operator fun invoke(shouldRefreshGifs: Boolean) =
        gifRepository.provideTrendingGifs(shouldRefreshGifs)

}