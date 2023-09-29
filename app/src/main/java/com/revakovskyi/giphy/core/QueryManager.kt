package com.revakovskyi.giphy.core

import javax.inject.Inject

interface QueryManager {
    fun verifyQuery(query: String): Status

    enum class Status {
        Correct, Incorrect, Neutral
    }

}


class QueryManagerImpl @Inject constructor() : QueryManager {

    override fun verifyQuery(query: String): QueryManager.Status {
        return if (query.isNotEmpty()) {
            if (isQueryValid(query)) QueryManager.Status.Correct
            else QueryManager.Status.Incorrect
        } else QueryManager.Status.Neutral
    }

    private fun isQueryValid(query: String): Boolean {
        val queryPattern = Regex("""^[a-zA-Z0-9_@].*""")
        return queryPattern.matches(query)
    }

}
