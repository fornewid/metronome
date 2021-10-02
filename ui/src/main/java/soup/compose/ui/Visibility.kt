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

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy

/**
 * [VisibilityState] contains the same three states as visibility of [android.view.View].
 *
 * @sample soup.compose.experimental.sample.ui.VisibilitySample
 * @see Visibility
 */
@ExperimentalComposeUiApi
enum class VisibilityState {
    /**
     * Same as [android.view.View.VISIBLE].
     */
    Visible,

    /**
     * Same as [android.view.View.INVISIBLE].
     */
    Invisible,

    /**
     * Same as [android.view.View.GONE].
     */
    Gone
}

/**
 * [Visibility] composable provides the same functionality as the visibility state of a [android.view.View].
 *
 * @sample soup.compose.experimental.sample.ui.VisibilitySample
 *
 * @param visibility One of [VisibilityState.Visible], [VisibilityState.Invisible], [VisibilityState.Gone]
 * @param modifier modifier for the [Layout] created to contain the [content]
 * @param content Content to appear or disappear based on the value of [visibility]
 *
 * @see Visibility
 * @see VisibilityState
 */
@ExperimentalComposeUiApi
@Composable
fun Visibility(
    visibility: VisibilityState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (visibility == VisibilityState.Visible || visibility == VisibilityState.Invisible) {
        // Nested layout is used to show layout bounds.
        NestedLayout(
            modifier = Modifier
                .invisible(visibility == VisibilityState.Invisible)
                .then(modifier),
            content = content
        )
    }
}

private fun Modifier.invisible(invisible: Boolean): Modifier {
    return if (invisible) {
        // If invisible, only measures but not draws content.
        drawWithContent { /* drawContent() */ }
    } else {
        this
    }
}

@Composable
private fun NestedLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout {
        Layout(
            modifier = modifier,
            content = content
        )
    }
}

@Composable
private fun Layout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        val placeables = measurables.fastMap { it.measure(constraints) }
        val maxWidth = placeables.fastMaxBy { it.width }?.width ?: 0
        val maxHeight = placeables.fastMaxBy { it.height }?.height ?: 0
        layout(maxWidth, maxHeight) {
            placeables.fastForEach { placeable ->
                placeable.place(0, 0)
            }
        }
    }
}
