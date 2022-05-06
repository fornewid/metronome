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
package soup.metronome.sample.readmore

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import soup.metronome.sample.theme.SampleTheme

private const val description: String =
    "abcdefghijklmnopqrstuvwxyz,abcdefghijklmnopqrstuvwxyz,abcdefghijklmnopqrstuvwxyz,abcdefghijklmnopqrstuvwxyz,abcdefghijklmnopqrstuvwxyz."

@Composable
fun ReadMoreTextDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ReadMoreTextDemo") }) },
        content = {
            Column(modifier = Modifier.padding(it).fillMaxSize()) {
                Item1()
                Divider()
                Item2()
                Divider()
                Item3()
                Divider()
                Item4()
                Divider()
            }
        }
    )
}

@Composable
private fun Item1() {
    ReadMoreText(
        text = "abcdefghijklmnopqrstuvwxyz",
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        readMoreMaxLines = 1
    )
}

@Composable
private fun Item2() {
    ReadMoreText(
        text = description,
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        readMoreMaxLines = 1
    )
}

@Composable
private fun Item3() {
    ReadMoreText(
        text = description,
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 100)),
        readMoreText = "Read more",
        readMoreMaxLines = 2
    )
}

@Composable
private fun Item4() {
    Column {
        Text(
            text = "Title",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, end = 18.dp, top = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        ReadMoreText(
            text = description,
            fontSize = 15.sp,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 18.dp, top = 5.dp, end = 18.dp, bottom = 18.dp),
            readMoreText = "Read more",
            readMoreMaxLines = 3,
            readMoreStyle = SpanStyle(
                color = MaterialTheme.colors.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    SampleTheme {
        ReadMoreTextDemo()
    }
}
