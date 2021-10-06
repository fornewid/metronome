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

import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipCheckIcon(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Checked,
        contentDescription = null,
        modifier = modifier.size(24.dp),
    )
}

private val Checked: ImageVector
    get() {
        if (_checked != null) {
            return _checked!!
        }
        _checked = materialIcon(name = "Chip.Checked") {
            materialPath {
                moveTo(9f, 16.2f)
                lineTo(4.8f, 12f)
                lineToRelative(-1.4f, 1.4f)
                lineTo(9f, 19f)
                lineTo(21f, 7f)
                lineToRelative(-1.4f, -1.4f)
                lineTo(9f, 16.2f)
                close()
            }
        }
        return _checked!!
    }

private var _checked: ImageVector? = null
