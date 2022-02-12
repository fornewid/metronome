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
@file:Suppress("unused")

package soup.metronome.internal.test

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.toPixelMap
import com.google.common.truth.Truth.assertWithMessage

private const val DefaultTolerance = 0.02f

/**
 * Asserts that the color at a specific pixel in the bitmap at ([x], [y]) is [expected].
 */
fun PixelMap.assertPixelColor(
    expected: Color,
    x: Int,
    y: Int,
    tolerance: Float = DefaultTolerance,
    error: (Color) -> String = { color -> "Pixel($x, $y) expected to be $expected, but was $color" }
) {
    val color = this[x, y]
    val errorString = error(color)
    assertWithMessage(errorString).that(color.red).isWithin(tolerance).of(expected.red)
    assertWithMessage(errorString).that(color.green).isWithin(tolerance).of(expected.green)
    assertWithMessage(errorString).that(color.blue).isWithin(tolerance).of(expected.blue)
    assertWithMessage(errorString).that(color.alpha).isWithin(tolerance).of(expected.alpha)
}

/**
 * Assert that all of the pixels in this image as of the [expected] color.
 */
fun ImageBitmap.assertPixels(expected: Color, tolerance: Float = DefaultTolerance) {
    val pixel = toPixelMap()
    for (x in 0 until width) {
        for (y in 0 until height) {
            pixel.assertPixelColor(expected, x, y, tolerance)
        }
    }
}

/**
 * Asserts that the colors at specific pixels in the vertices of bitmap is [expected].
 */
fun ImageBitmap.assertPixelsOfVertices(expected: Color, tolerance: Float = DefaultTolerance) {
    toPixelMap().run {
        assertPixelColor(expected, 0, 0, tolerance)
        assertPixelColor(expected, 0, height - 1, tolerance)
        assertPixelColor(expected, width - 1, 0, tolerance)
        assertPixelColor(expected, width - 1, height - 1, tolerance)
    }
}

/**
 * Asserts that the color at specific pixel in the center of bitmap is [expected].
 */
fun ImageBitmap.assertPixelOfCenter(expected: Color, tolerance: Float = DefaultTolerance) {
    toPixelMap().assertPixelColor(expected, width / 2, height / 2, tolerance)
}
