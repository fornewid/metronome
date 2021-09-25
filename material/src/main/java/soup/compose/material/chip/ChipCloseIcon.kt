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
package soup.compose.material.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipCloseIcon(
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = CloseCircle,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(horizontal = 2.dp)
            .size(18.dp)
    )
}

private val CloseCircle: ImageVector
    get() {
        if (_closeCircle != null) {
            return _closeCircle!!
        }
        _closeCircle = ImageVector.Builder(
            name = "Chip.CloseCircle",
            defaultWidth = 18.dp,
            defaultHeight = 18.dp,
            viewportWidth = 18f,
            viewportHeight = 18f
        ).apply {
            materialPath {
                moveTo(13f, 11.87f)
                lineTo(11.87f, 13f)
                lineTo(9f, 10.13f)
                lineTo(9f, 10.13f)
                lineTo(6.13f, 13f)
                lineTo(5f, 11.87f)
                lineTo(7.87f, 9f)
                lineTo(5f, 6.13f)
                lineTo(6.13f, 5f)
                lineTo(9f, 7.87f)
                lineTo(11.87f, 5f)
                lineTo(13f, 6.13f)
                lineTo(10.13f, 9f)
                lineTo(13f, 11.87f)
                close()
                moveTo(9f, 1f)
                curveTo(4.58f, 1f, 1f, 4.58f, 1f, 9f)
                curveTo(1f, 13.42f, 4.58f, 17f, 9f, 17f)
                curveTo(13.42f, 17f, 17f, 13.42f, 17f, 9f)
                curveTo(17f, 4.58f, 13.42f, 1f, 9f, 1f)
                lineTo(9f, 1f)
                close()
            }
        }.build()
        return _closeCircle!!
    }

private var _closeCircle: ImageVector? = null
