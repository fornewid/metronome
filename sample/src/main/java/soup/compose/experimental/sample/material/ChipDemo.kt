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
package soup.compose.experimental.sample.material

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soup.compose.experimental.sample.R
import soup.compose.experimental.sample.theme.SampleMaterialTheme
import soup.compose.experimental.sample.theme.SampleTheme
import soup.compose.material.chip.ActionChip
import soup.compose.material.chip.ChoiceChip
import soup.compose.material.chip.EntryChip
import soup.compose.material.chip.FilterChip

@Composable
fun EntryChipSample(text: String, enabled: Boolean) {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    EntryChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        onCloseIconClick = { /* Do something */ },
        chipIcon = {
            Image(
                painterResource(R.drawable.ic_placeholder_circle_24),
                contentDescription = null,
            )
        },
        enabled = enabled
    ) {
        Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun FilterChipSample(text: String, enabled: Boolean) {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    FilterChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled
    ) {
        Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun ChoiceChipSample(text: String, enabled: Boolean) {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    ChoiceChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled
    ) {
        Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun ActionChipSample(text: String, enabled: Boolean) {
    ActionChip(
        onClick = { /* Do something! */ },
        chipIcon = {
            Image(
                painterResource(R.drawable.ic_placeholder_circle_24),
                contentDescription = null,
            )
        },
        enabled = enabled
    ) {
        Text(text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun ChipDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ChipDemo") }) },
        content = {
            SampleMaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .padding(it)
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        val (longText, onLongTextChange) = remember { mutableStateOf(false) }
                        val (enabled, onEnabledChange) = remember { mutableStateOf(true) }

                        val text = if (longText) {
                            "Hello, very very very very very very very very big World!"
                        } else {
                            "Hello, World!"
                        }

                        Text("Entry")
                        EntryChipSample(text, enabled)
                        Spacer(Modifier.height(16.dp))

                        Text("Filter")
                        FilterChipSample(text, enabled)
                        Spacer(Modifier.height(16.dp))

                        Text("Choice")
                        ChoiceChipSample(text, enabled)
                        Spacer(Modifier.height(16.dp))

                        Text("Action")
                        ActionChipSample(text, enabled)
                        Spacer(Modifier.height(16.dp))

                        Column(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                Modifier.height(48.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Long text")
                                Spacer(Modifier.size(8.dp))
                                Switch(checked = longText, onCheckedChange = onLongTextChange)
                            }
                            Spacer(Modifier.height(16.dp))
                            Row(
                                Modifier.height(48.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Enabled")
                                Spacer(Modifier.size(8.dp))
                                Switch(checked = enabled, onCheckedChange = onEnabledChange)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    SampleTheme {
        ChipDemo()
    }
}
