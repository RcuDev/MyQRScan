package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction

@Composable
fun QRAutofocusTextField(
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    TextField(
        value = value,
        singleLine = true,
        modifier = Modifier.fillMaxWidth().focusRequester(focusRequester = focusRequester),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onValueChange = { onValueChanged(it) }
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}