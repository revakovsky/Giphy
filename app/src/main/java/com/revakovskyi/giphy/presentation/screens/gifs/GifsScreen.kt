package com.revakovskyi.giphy.presentation.screens.gifs

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.revakovskyi.giphy.presentation.screens.gifs.mvi.GifsEvent
import com.revakovskyi.giphy.presentation.screens.gifs.mvi.GifsState

@Composable
fun GifsScreen(
    onOpenGifInfoScreen: (String) -> Unit,
    onEvent: (GifsEvent) -> Unit,
    state: GifsState,
) {

    val context = LocalContext.current

    Log.d("TAG_Max", "state = $state")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {

    }

    BackHandler { (context as Activity).finish() }

}
