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

import androidx.annotation.FloatRange
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateTo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.takeOrElse

@Composable
fun rememberZoomableState(
    @FloatRange(from = 1.0) initialScale: Float = 1f,
    initialOffset: Offset = Offset.Zero,
    minimumScale: Float = 1f,
    maximumScale: Float = 3f,
): ZoomableState = rememberSaveable(saver = ZoomableState.Saver) {
    ZoomableState(
        currentScale = initialScale,
        currentOffset = initialOffset,
        minimumScale = minimumScale,
        maximumScale = maximumScale,
    )
}

@Stable
class ZoomableState(
    @FloatRange(from = 1.0) currentScale: Float = 1f,
    currentOffset: Offset = Offset.Zero,
    internal val minimumScale: Float = 1f,
    internal val maximumScale: Float = 3f,
) {
    internal var boxSize: Size = Size.Zero
    var imageSize: Size = Size.Unspecified

    private var _currentScale by mutableStateOf(currentScale)

    @get:FloatRange(from = 1.0)
    internal var currentScale: Float
        get() = _currentScale
        internal set(value) {
            val coerceValue = value.coerceIn(minimumScale, maximumScale)
            if (coerceValue != _currentScale) {
                _currentScale = coerceValue
            }
        }

    private var _currentOffset by mutableStateOf(currentOffset)

    internal var currentOffset: Offset
        get() = _currentOffset
        internal set(value) {
            val image = imageSize.takeOrElse { boxSize }
            val scrollableX = ((image.width * currentScale - boxSize.width) / 2).coerceAtLeast(0f)
            val scrollableY = ((image.height * currentScale - boxSize.height) / 2).coerceAtLeast(0f)
            val coerceValue = Offset(
                value.x.coerceIn(-scrollableX, scrollableX),
                value.y.coerceIn(-scrollableY, scrollableY),
            )
            if (coerceValue != _currentOffset) {
                _currentOffset = coerceValue
            }
        }

    val isScaled: Boolean
        get() = currentScale != 1f || currentOffset != Offset.Zero

    suspend fun animateToMinimum() {
        val initialScale = currentScale
        val targetScale = minimumScale
        val initialOffset = currentOffset
        val targetOffset = Offset.Zero
        if (initialScale != targetScale || initialOffset != targetOffset) {
            val scaleDiff = targetScale - initialScale
            val offsetDiff = targetOffset - initialOffset
            val anim = AnimationState(initialValue = 0f)
            anim.animateTo(targetValue = 1f) {
                currentScale = initialScale + scaleDiff * value
                currentOffset = initialOffset + offsetDiff * value
            }
        }
    }

    suspend fun animateToMaximum() {
        val initialScale = currentScale
        val targetScale = maximumScale
        if (initialScale != targetScale) {
            val diff = targetScale - initialScale
            val anim = AnimationState(initialValue = 0f)
            anim.animateTo(targetValue = 1f) {
                currentScale = initialScale + diff * value
            }
        }
    }

    companion object {
        /**
         * The default [Saver] implementation for [ZoomableState].
         */
        val Saver: Saver<ZoomableState, *> = listSaver(
            save = {
                listOf<Any>(
                    it.currentScale,
                    it.currentOffset.x,
                    it.currentOffset.y,
                    it.minimumScale,
                    it.maximumScale,
                )
            },
            restore = {
                ZoomableState(
                    currentScale = it[0] as Float,
                    currentOffset = Offset(
                        x = it[1] as Float,
                        y = it[2] as Float,
                    ),
                    minimumScale = it[3] as Float,
                    maximumScale = it[4] as Float,
                )
            }
        )
    }
}
