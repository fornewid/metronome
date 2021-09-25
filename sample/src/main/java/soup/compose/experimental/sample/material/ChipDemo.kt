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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soup.compose.experimental.sample.theme.SampleMaterialTheme
import soup.compose.experimental.sample.theme.SampleTheme
import soup.compose.material.chip.Chip
import soup.compose.material.chip.FilterChip

@Composable
fun ChipSample() {
    Chip(onClick = { /* Do something! */ }) {
        Text(text = "Chip")
    }
}

@Composable
fun ChipWithIconSample() {
    Chip(
        onClick = { /* Do something! */ },
        chipIcon = {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null
            )
        }
    ) {
        Text(text = "Like")
    }
}

@Composable
fun ChipWithCloseIconSample() {
    Chip(
        onClick = { /* Do something! */ },
        onCloseIconClick = { /* Do something! */ },
        chipIcon = {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = null
            )
        }
    ) {
        Text(text = "Like")
    }
}

@Composable
fun FilterChipSample() {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    FilterChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        onCloseIconClick = { /* Do something! */ }
    ) {
        Text(text = "Like")
    }
}

@Composable
fun ChipDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ChipDemo") }) },
        content = {
            SampleMaterialTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .background(MaterialTheme.colors.background),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ChipSample()
                    Spacer(Modifier.height(24.dp))
                    ChipWithIconSample()
                    Spacer(Modifier.height(24.dp))
                    ChipWithCloseIconSample()
                    Spacer(Modifier.height(24.dp))
                    FilterChipSample()
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
