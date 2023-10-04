package com.revakovskyi.domain.useCases

import com.revakovskyi.domain.repository.GifRepository
import com.revakovskyi.domain.util.DataResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSearchedGifsUseCase @Inject constructor(
    private val gifRepository: GifRepository,
) {

    suspend operator fun invoke(query: String): DataResult<List<String>> =
        gifRepository.provideSearchedGifsUrls(query)

}