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
package soup.metronome.sample.zoomable

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import kotlinx.coroutines.launch
import soup.metronome.sample.R
import soup.metronome.sample.theme.SampleTheme
import soup.metronome.zoomable.ZoomableBox
import soup.metronome.zoomable.ZoomableState
import soup.metronome.zoomable.rememberZoomableState

@Composable
fun ZoomableBoxDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ZoomableBoxDemo") }) }
    ) {
        BoxWithConstraints(modifier = Modifier.padding(it)) {
            val zoomableStateForLocal: ZoomableState = rememberZoomableState()
            val zoomableStateForRemote: ZoomableState = rememberZoomableState()
            if (maxWidth < maxHeight) {
                Column {
                    ZoomableBoxWithLocalImage(
                        zoomableState = zoomableStateForLocal,
                        modifier = Modifier.weight(1f)
                    )
                    Divider()
                    ZoomableBoxWithRemoteImage(
                        zoomableState = zoomableStateForRemote,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                Row {
                    ZoomableBoxWithLocalImage(
                        zoomableState = zoomableStateForLocal,
                        modifier = Modifier.weight(1f)
                    )
                    Divider(modifier = Modifier.width(1.dp).fillMaxHeight())
                    ZoomableBoxWithRemoteImage(
                        zoomableState = zoomableStateForRemote,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ZoomableBoxWithLocalImage(
    zoomableState: ZoomableState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = zoomableState.isScaled) {
        coroutineScope.launch {
            zoomableState.animateToMinimum()
        }
    }
    val painter = painterResource(R.drawable.wallpaper)
    ZoomableBox(
        imageSize = painter.intrinsicSize,
        state = zoomableState,
        modifier = modifier,
    ) {
        Image(
            painter,
            contentDescription = "local",
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun ZoomableBoxWithRemoteImage(
    zoomableState: ZoomableState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = zoomableState.isScaled) {
        coroutineScope.launch {
            zoomableState.animateToMinimum()
        }
    }
    var imageSize: Size by remember { mutableStateOf(Size.Zero) }
    ZoomableBox(
        imageSize = imageSize,
        state = zoomableState,
        modifier = modifier,
    ) {
        AsyncImage(
            "https://raw.githubusercontent.com/Baseflow/PhotoView/master/sample/src/main/res/drawable-nodpi/wallpaper.jpg",
            contentDescription = "remote",
            modifier = Modifier.fillMaxSize(),
            transform = {
                if (it is AsyncImagePainter.State.Success) {
                    imageSize = it.painter.intrinsicSize
                }
                it
            }
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    SampleTheme {
        ZoomableBoxDemo()
    }
}
