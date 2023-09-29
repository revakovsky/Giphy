package com.revakovskyi.data.utils

import android.content.Context
import com.revakovskyi.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

internal interface ExceptionHandler {
    fun handleException(e: Exception): String
}


internal class ExceptionHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ExceptionHandler {

    override fun handleException(e: Exception): String {
        e.printStackTrace()
        return when (e) {
            is HttpException -> context.getString(R.string.could_not_load_from_the_server)
            is CancellationException -> throw e
            else -> context.getString(R.string.something_went_wrong)
        }
    }

}
