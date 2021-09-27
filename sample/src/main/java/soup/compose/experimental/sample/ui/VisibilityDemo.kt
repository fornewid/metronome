/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package soup.compose.experimental.sample.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soup.compose.experimental.sample.theme.SampleTheme
import soup.compose.ui.Visibility
import soup.compose.ui.VisibilityState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VisibilitySample() {
    Visibility(visibility = VisibilityState.Invisible) {
        Text("Invisible Text")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VisibilityDemo() {
    val options = VisibilityState.values().toList()
    val (selectedOption, onOptionSelected) = rememberSaveable {
        mutableStateOf(options.first())
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "VisibilityDemo") }) },
        content = {
            Column(modifier = Modifier.padding(it)) {
                ColorText("Top", Color.Red)
                Visibility(visibility = selectedOption) {
                    ColorText("Visibility", Color.Green)
                }
                ColorText("Bottom", Color.Blue)

                Spacer(Modifier.height(24.dp))

                Row {
                    ColorText("Left", Color.Red)
                    Visibility(visibility = selectedOption) {
                        ColorText("Visibility", Color.Green)
                    }
                    ColorText("Right", Color.Blue)
                }
            }
        },
        bottomBar = { BottomBar(options, selectedOption, onOptionSelected) }
    )
}

@Composable
private fun ColorText(text: String, color: Color) {
    Text(
        text = text,
        color = if (color.luminance() < 0.5) Color.White else Color.Black,
        modifier = Modifier
            .background(color)
            .padding(24.dp)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BottomBar(
    options: List<VisibilityState>,
    selectedOption: VisibilityState,
    onOptionSelected: (VisibilityState) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        options.forEach { option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = null
                )
                Text(
                    text = option.name,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    SampleTheme {
        VisibilityDemo()
    }
}
