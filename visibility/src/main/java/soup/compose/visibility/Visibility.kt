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
package soup.compose.visibility

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints

/**
 * A [Modifier.Element] that controls the content drawing.
 *
 * @sample soup.compose.extensions.sample.ui.VisibleSample
 *
 * @param visible true sets the visibility to [android.view.View.VISIBLE], false to [android.view.View.INVISIBLE].
 */
@ExperimentalComposeUiApi
fun Modifier.visible(visible: Boolean): Modifier {
    return if (visible) {
        this
    } else {
        this.then(Invisible)
    }
}

/**
 * A [Modifier.Element] that controls the content drawing.
 *
 * @sample soup.compose.extensions.sample.ui.InvisibleSample
 *
 * @param invisible true sets the visibility to [android.view.View.INVISIBLE], false to [android.view.View.VISIBLE].
 */
@ExperimentalComposeUiApi
fun Modifier.invisible(invisible: Boolean): Modifier {
    return if (invisible) {
        this.then(Invisible)
    } else {
        this
    }
}

private object Invisible : LayoutModifier, DrawModifier {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable: Placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun ContentDrawScope.draw() {
        // only measures but not draws content.
    }
}
