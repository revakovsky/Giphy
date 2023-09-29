package com.revakovskyi.giphy.core

import javax.inject.Inject

interface QueryManager {
    fun verifyQuery(query: String): Status
}


class QueryManagerImpl @Inject constructor() : QueryManager {

    override fun verifyQuery(query: String): Status {
        return if (query.isNotEmpty()) {
            if (isQueryValid(query)) Status.Correct
            else Status.Incorrect
        } else Status.Neutral
    }

    private fun isQueryValid(query: String): Boolean {
        val queryPattern = Regex("""^[a-zA-Z0-9_@].*""")
        return queryPattern.matches(query)
    }

}
