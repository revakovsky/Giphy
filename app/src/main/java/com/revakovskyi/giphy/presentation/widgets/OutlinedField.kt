package com.revakovskyi.giphy.presentation.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.core.QueryManager
import com.revakovskyi.giphy.presentation.ui.theme.dimens

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OutlinedField(
    modifier: Modifier = Modifier,
    value: String,
    status: QueryManager.Status = QueryManager.Status.Neutral,
    onValueChange: (String) -> Unit,
    padding: Dp = MaterialTheme.dimens.medium,
) {
    val controller = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = stringResource(R.string.input_a_search_query),
                style = MaterialTheme.typography.bodySmall
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.start_typing),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = padding, top = padding, end = padding),
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
        supportingText = {
            if (status == QueryManager.Status.Incorrect) Text(
                text = stringResource(R.string.incorrect_input),
                color = MaterialTheme.colorScheme.errorContainer,
                style = MaterialTheme.typography.bodySmall
            )
        },
        isError = status == QueryManager.Status.Incorrect,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            autoCorrect = true,
            capitalization = KeyboardCapitalization.None
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                controller?.hide()
                focusManager.clearFocus()
            },
        ),
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,

            focusedLabelColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),

            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
        ),
    )

}
