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
package soup.metronome.sample.material

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soup.metronome.material.UnelevatedButton
import soup.metronome.sample.theme.SampleMaterialTheme
import soup.metronome.sample.theme.SampleTheme

@Composable
fun UnelevatedButtonSample() {
    UnelevatedButton(onClick = { /* Do something! */ }) {
        Text(text = "UnelevatedButton")
    }
}

@Composable
fun UnelevatedButtonWithIconSample() {
    UnelevatedButton(onClick = { /* Do something! */ }) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Like")
    }
}

@Composable
fun UnelevatedButtonDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ButtonDemo") }) },
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SampleMaterialTheme {
                    UnelevatedButtonSample()
                    Spacer(Modifier.height(24.dp))
                    UnelevatedButtonWithIconSample()
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
        UnelevatedButtonDemo()
    }
}
