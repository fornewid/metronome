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
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import soup.metronome.sample.R
import soup.metronome.sample.theme.SampleTheme

@Composable
fun ZoomableBoxDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ZoomableBoxDemo") }) },
        content = {
            ZoomableBox(
                modifier = Modifier
                    .padding(it)
                    .background(Color.Black)
            ) {
                Image(
                    painterResource(R.drawable.wallpaper),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    )
}

@Composable
private fun ZoomableBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    propagateMinConstraints: Boolean = false,
    minScale: Float = 1f,
    maxScale: Float = 3f,
    content: @Composable BoxScope.() -> Unit
) {
    BoxWithConstraints(modifier = modifier) {
        val density = LocalDensity.current
        val maxWidth = with(density) { maxWidth.toPx() }
        val maxHeight = with(density) { maxHeight.toPx() }

        var scale by remember { mutableStateOf(minScale) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, _ ->
            scale = (scale * zoomChange).coerceIn(minScale, maxScale)
            val availableW = (maxWidth * scale - maxWidth) / 2
            val availableH = (maxHeight * scale - maxHeight) / 2
            offset = Offset(
                x = (offset.x + offsetChange.x).coerceIn(-availableW, availableW),
                y = (offset.y + offsetChange.y).coerceIn(-availableH, availableH),
            )
        }
        BackHandler(enabled = scale > minScale || offset != Offset.Zero) {
            scale = minScale
            offset = Offset.Zero
        }
        Box(
            modifier = Modifier
                .pointerInput(scale) {
                    val availableW = (maxWidth * scale - maxWidth) / 2
                    val availableH = (maxHeight * scale - maxHeight) / 2
                    detectDragGestures { _, dragAmount ->
                        offset = Offset(
                            x = (offset.x + dragAmount.x).coerceIn(-availableW, availableW),
                            y = (offset.y + dragAmount.y).coerceIn(-availableH, availableH),
                        )
                    }
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            if (scale >= maxScale) {
                                scale = minScale
                                offset = Offset.Zero
                            } else {
                                scale = maxScale
                            }
                        }
                    )
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state = state)
                .fillMaxSize(),
            contentAlignment = contentAlignment,
            propagateMinConstraints = propagateMinConstraints,
            content = content
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
