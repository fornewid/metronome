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
package soup.compose.ui

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

/**
 * [Invisible] composable provides the same functionality as [androidx.core.view.isInvisible].
 *
 * @sample soup.compose.experimental.sample.ui.InvisibleSample
 *
 * @param invisible Setting this property to true sets the visibility to [View.INVISIBLE], false to [View.VISIBLE].
 * @param modifier modifier for the [Layout] created to contain the [content]
 * @param content Content to appear or disappear based on the value of [invisible]
 */
@ExperimentalComposeUiApi
@Composable
fun Invisible(
    invisible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = { content() },
        modifier = Modifier
            .invisible(invisible)
            .then(modifier)
    ) { measurables, constraints ->
        val placeables = measurables.fastMap { it.measure(constraints) }
        val maxWidth: Int = placeables.fastMaxBy { it.width }?.width ?: 0
        val maxHeight = placeables.fastMaxBy { it.height }?.height ?: 0
        layout(maxWidth, maxHeight) {
            placeables.fastForEach {
                it.place(0, 0)
            }
        }
    }
}

@ExperimentalComposeUiApi
private fun Modifier.invisible(invisible: Boolean): Modifier {
    return if (invisible) {
        // If invisible, only measures but not draws content.
        drawWithContent { /* drawContent() */ }
    } else {
        this
    }
}
