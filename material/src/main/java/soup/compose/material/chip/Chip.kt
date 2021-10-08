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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @sample soup.compose.experimental.sample.material.FilterChipSample
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterChip(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    closeIcon: @Composable () -> Unit = { ChipCloseIcon(contentDescription = null) },
    onCloseIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.chipColors(),
    contentPadding: PaddingValues = if (onCloseIconClick == null) {
        ChipDefaults.ContentPadding
    } else {
        PaddingValues(start = ChipDefaults.ContentPaddingStart)
    },
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    val checkedIcon: (@Composable () -> Unit)? = if (checked) {
        @Composable { ChipCheckIcon() }
    } else {
        null
    }
    Chip(
        onClick = { onCheckedChange(checked.not()) },
        modifier = modifier,
        chipIcon = checkedIcon,
        closeIcon = closeIcon,
        onCloseIconClick = onCloseIconClick,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        minTouchTargetSize = minTouchTargetSize
    ) {
        content()
    }
}

/**
 * @sample soup.compose.experimental.sample.material.ChipSample
 * @sample soup.compose.experimental.sample.material.ChipWithIconSample
 * @sample soup.compose.experimental.sample.material.ChipWithCloseIconSample
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Chip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    chipIcon: (@Composable () -> Unit)? = null,
    closeIcon: @Composable () -> Unit = { ChipCloseIcon(contentDescription = null) },
    onCloseIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.chipColors(),
    contentPadding: PaddingValues = if (onCloseIconClick == null) {
        ChipDefaults.ContentPadding
    } else {
        PaddingValues(start = ChipDefaults.ContentPaddingStart)
    },
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    val minTouchTargetSizeModifier = if (minTouchTargetSize > ChipDefaults.MinHeight) {
        Modifier.padding(vertical = (minTouchTargetSize - ChipDefaults.MinHeight) / 2f)
    } else {
        Modifier
    }
    BaseChip(
        onClick = onClick,
        modifier = modifier.then(minTouchTargetSizeModifier),
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding
    ) {
        if (chipIcon != null) {
            chipIcon()
        }
        Row(modifier = Modifier.padding(ChipDefaults.TextPadding)) {
            content()
        }
        if (onCloseIconClick != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clickable(
                        onClick = onCloseIconClick,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .fillMaxHeight()
                    .padding(end = ChipDefaults.ContentPaddingEnd)
            ) {
                closeIcon()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BaseChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.chipColors(),
    contentPadding: PaddingValues = ChipDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by colors.contentColor(enabled)
    Surface(
        modifier = modifier,
        shape = shape,
        color = colors.backgroundColor(enabled).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation.elevation(enabled, interactionSource).value,
        onClick = onClick,
        enabled = enabled,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple()
    ) {
        CompositionLocalProvider(LocalContentAlpha provides contentColor.alpha) {
            ProvideTextStyle(
                value = MaterialTheme.typography.body2
            ) {
                Row(
                    modifier = Modifier
                        .height(ChipDefaults.MinHeight)
                        .padding(contentPadding),
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        }
    }
}

@Stable
interface ChipElevation {

    /**
     * Represents the elevation used in a chip, depending on [enabled] and
     * [interactionSource].
     *
     * @param enabled whether the chip is enabled
     * @param interactionSource the [InteractionSource] for this chip
     */
    @Composable
    fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp>
}

@Stable
interface ChipColors {

    @Composable
    fun backgroundColor(enabled: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean): State<Color>
}

object ChipDefaults {

    internal val Shape = RoundedCornerShape(percent = 50)

    internal val ContentPaddingStart = 4.dp
    internal val ContentPaddingEnd = 6.dp
    val ContentPadding = PaddingValues(start = ContentPaddingStart, end = ContentPaddingEnd)

    val MinHeight = 32.dp
    val MinTouchTargetSize = 48.dp

    val TextPadding = PaddingValues(start = 8.dp, end = 6.dp)

    @Composable
    fun elevation(
        defaultElevation: Dp = 0.dp,
        pressedElevation: Dp = 3.dp,
        disabledElevation: Dp = 0.dp
    ): ChipElevation = object : ChipElevation {

        private val elevation = ButtonDefaults.elevation(
            defaultElevation = defaultElevation,
            pressedElevation = pressedElevation,
            disabledElevation = disabledElevation
        )

        @Composable
        override fun elevation(enabled: Boolean, interactionSource: InteractionSource): State<Dp> {
            return elevation.elevation(enabled = enabled, interactionSource = interactionSource)
        }
    }

    @Composable
    fun chipColors(
        backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.10f)
            .compositeOver(MaterialTheme.colors.surface),
        contentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.33f)
    ): ChipColors = DefaultChipColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor
    )
}

/**
 * Default [ChipColors] implementation.
 */
@Immutable
private class DefaultChipColors(
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledContentColor: Color
) : ChipColors {
    @Composable
    override fun backgroundColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) backgroundColor else disabledBackgroundColor)
    }

    @Composable
    override fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultChipColors

        if (backgroundColor != other.backgroundColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledBackgroundColor != other.disabledBackgroundColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = backgroundColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledBackgroundColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}
