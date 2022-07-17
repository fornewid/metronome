/*
 * Copyright 2022 SOUP
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
package soup.metronome.zoomable

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.launch

@Composable
fun ZoomableBox(
    modifier: Modifier = Modifier,
    state: ZoomableState = rememberZoomableState(),
    content: @Composable BoxScope.() -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val transformableState = rememberTransformableState { zoomChange, offsetChange, _ ->
        state.currentScale *= zoomChange
        state.currentOffset += offsetChange
    }
    Box(
        modifier = modifier
            .onSizeChanged { state.boxSize = it.toSize() }
            .pointerInput(state.currentScale) {
                detectDragGestures { _, dragAmount ->
                    state.currentOffset += dragAmount
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (state.currentScale >= state.maximumScale) {
                            coroutineScope.launch {
                                state.animateToMinimum()
                            }
                        } else {
                            coroutineScope.launch {
                                state.animateToMaximum()
                            }
                        }
                    }
                )
            }
            .clipToBounds()
            .graphicsLayer {
                scaleX = state.currentScale
                scaleY = state.currentScale
                translationX = state.currentOffset.x
                translationY = state.currentOffset.y
            }
            .transformable(state = transformableState),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false,
        content = content,
    )
}
