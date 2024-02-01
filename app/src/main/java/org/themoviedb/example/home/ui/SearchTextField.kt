@file:OptIn(ExperimentalComposeUiApi::class)

package org.themoviedb.example.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

import org.themoviedb.example.R

@Composable
fun SearchTextField(
    keyboardController: SoftwareKeyboardController,
    focusManager: FocusManager,
    enabled: Boolean,
    onSearch: (searchText: TextFieldValue) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    BasicTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch.invoke(searchText)
        },
        enabled = enabled,
        //textStyle = textFieldStyle,
        cursorBrush = SolidColor(colorResource(id = R.color.white)),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            keyboardController.hide()
            focusManager.clearFocus()
            onSearch.invoke(searchText)
        }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 0.5.dp,
                        color = Color.DarkGray.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = colorResource(id = R.color.black)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (searchText.text.isEmpty()) {
                        Text(
                            text = "Search Movies",
                            color = Color.Black
                        )
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (searchText.text.isNotEmpty()) {
                    Icon(imageVector = Icons.Rounded.Clear,
                        contentDescription = "Search Clear",
                        tint = colorResource(id = R.color.black),
                        modifier = Modifier.clickable {
                            searchText = TextFieldValue("")
                            onClear.invoke()
                        })
                }
            }
        },
        modifier = modifier
            .padding(10.dp)
            .background(Color.White.copy(alpha = 0.5f))
    )
}