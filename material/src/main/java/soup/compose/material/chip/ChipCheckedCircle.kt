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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

internal val ChipCheckedCircle: ImageVector
    get() {
        if (_chipCheckedCircle != null) {
            return _chipCheckedCircle!!
        }
        _chipCheckedCircle = materialIcon(name = "Chip.CheckedCircle") {
            path(fill = SolidColor(Color(0xFF191919)), fillAlpha = 0.5f) {
                moveTo(12f, 12f)
                moveToRelative(-12f, 0f)
                arcToRelative(12f, 12f, 0f, true, true, 24f, 0f)
                arcToRelative(12f, 12f, 0f, true, true, -24f, 0f)
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(9.189f, 15.939f)
                lineToRelative(-3.127f, -3.128f)
                lineToRelative(-1.061f, 1.061f)
                lineToRelative(4.189f, 4.189f)
                lineToRelative(9f, -9f)
                lineToRelative(-1.061f, -1.061f)
                close()
            }
        }
        return _chipCheckedCircle!!
    }

private var _chipCheckedCircle: ImageVector? = null
