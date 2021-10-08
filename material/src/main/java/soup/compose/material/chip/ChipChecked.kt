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

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

internal val ChipChecked: ImageVector
    get() {
        if (_chipChecked != null) {
            return _chipChecked!!
        }
        _chipChecked = materialIcon(name = "Chip.Checked") {
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
        return _chipChecked!!
    }

private var _chipChecked: ImageVector? = null
