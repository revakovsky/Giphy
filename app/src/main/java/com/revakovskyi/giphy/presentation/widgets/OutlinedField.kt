package com.revakovskyi.giphy.presentation.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.revakovskyi.giphy.R
import com.revakovskyi.giphy.core.QueryManager
import com.revakovskyi.giphy.presentation.ui.theme.dimens
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun OutlinedField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    padding: Dp = MaterialTheme.dimens.medium,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
    status: QueryManager.Status = QueryManager.Status.Neutral,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    autoCorrect: Boolean = true,
    singleLine: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    bringIntoViewRequester: BringIntoViewRequester = BringIntoViewRequester(),
) {
    val controller = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

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
            .padding(start = padding, top = padding, end = padding)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        textStyle = textStyle,
        supportingText = {
            if (status == QueryManager.Status.Incorrect) Text(
                text = stringResource(R.string.incorrect_input),
                color = MaterialTheme.colorScheme.errorContainer,
                style = MaterialTheme.typography.bodySmall
            )
        },
        isError = status == QueryManager.Status.Incorrect,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType,
            autoCorrect = autoCorrect,
            capitalization = KeyboardCapitalization.None
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                controller?.hide()
                focusManager.clearFocus()
            },
        ),
        singleLine = singleLine,
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,

            focusedLabelColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f),

            unfocusedPlaceholderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
        ),
    )

}
