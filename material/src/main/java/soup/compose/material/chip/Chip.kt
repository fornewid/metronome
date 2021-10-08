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
import androidx.compose.foundation.Image
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
import androidx.compose.material.Icon
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
 * @sample soup.compose.experimental.sample.material.EntryChipSample
 */
@ExperimentalMaterialApi
@Composable
fun EntryChip(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    chipIcon: (@Composable () -> Unit)? = null,
    checkedIcon: @Composable () -> Unit = { Image(ChipCheckedCircle, contentDescription = null) },
    closeIcon: @Composable () -> Unit = { ChipCloseIcon(contentDescription = null) },
    onCloseIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.entryChipColors(),
    rippleColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = if (onCloseIconClick == null) {
        ChipDefaults.ContentPadding
    } else {
        PaddingValues(start = ChipDefaults.ContentPaddingStart)
    },
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    CoreChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        rippleColor = rippleColor,
        contentPadding = contentPadding,
        minTouchTargetSize = minTouchTargetSize
    ) {
        Box {
            if (chipIcon != null) {
                chipIcon()
            }
            if (checked) {
                checkedIcon()
            }
        }
        Row(
            modifier = Modifier
                .padding(ChipDefaults.TextPadding)
                .weight(1f, fill = false)
        ) {
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

/**
 * @sample soup.compose.experimental.sample.material.FilterChipSample
 */
@ExperimentalMaterialApi
@Composable
fun FilterChip(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkedIcon: @Composable () -> Unit = { Icon(ChipChecked, contentDescription = null) },
    closeIcon: @Composable () -> Unit = { ChipCloseIcon(contentDescription = null) },
    onCloseIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.filterChipColors(),
    rippleColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = if (onCloseIconClick == null) {
        ChipDefaults.ContentPadding
    } else {
        PaddingValues(start = ChipDefaults.ContentPaddingStart)
    },
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    CoreChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        rippleColor = rippleColor,
        contentPadding = contentPadding,
        minTouchTargetSize = minTouchTargetSize
    ) {
        if (checked) {
            checkedIcon()
        }
        Row(
            modifier = Modifier
                .padding(ChipDefaults.TextPadding)
                .weight(1f, fill = false)
        ) {
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

/**
 * @sample soup.compose.experimental.sample.material.ChoiceChipSample
 */
@ExperimentalMaterialApi
@Composable
fun ChoiceChip(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    chipIcon: (@Composable () -> Unit)? = null,
    closeIcon: @Composable () -> Unit = { ChipCloseIcon(contentDescription = null) },
    onCloseIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors = ChipDefaults.choiceChipColors(),
    rippleColor: Color = MaterialTheme.colors.primary,
    contentPadding: PaddingValues = if (onCloseIconClick == null) {
        ChipDefaults.ContentPadding
    } else {
        PaddingValues(start = ChipDefaults.ContentPaddingStart)
    },
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    CoreChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        rippleColor = rippleColor,
        contentPadding = contentPadding,
        minTouchTargetSize = minTouchTargetSize
    ) {
        if (chipIcon != null) {
            chipIcon()
        }
        Row(
            modifier = Modifier
                .padding(ChipDefaults.TextPadding)
                .weight(1f, fill = false)
        ) {
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

/**
 * @sample soup.compose.experimental.sample.material.ActionChipSample
 */
@ExperimentalMaterialApi
@Composable
fun ActionChip(
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
    colors: ChipColors = ChipDefaults.actionChipColors(),
    rippleColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = if (onCloseIconClick == null) {
        ChipDefaults.ContentPadding
    } else {
        PaddingValues(start = ChipDefaults.ContentPaddingStart)
    },
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    CoreChip(
        checked = false,
        onCheckedChange = { onClick() },
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        rippleColor = rippleColor,
        contentPadding = contentPadding,
        minTouchTargetSize = minTouchTargetSize
    ) {
        if (chipIcon != null) {
            chipIcon()
        }
        Row(
            modifier = Modifier
                .padding(ChipDefaults.TextPadding)
                .weight(1f, fill = false)
        ) {
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
private fun CoreChip(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ChipElevation = ChipDefaults.elevation(),
    shape: Shape = ChipDefaults.Shape,
    border: BorderStroke? = null,
    colors: ChipColors,
    rippleColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = ChipDefaults.ContentPadding,
    minTouchTargetSize: Dp = ChipDefaults.MinTouchTargetSize,
    content: @Composable RowScope.() -> Unit
) {
    val minTouchTargetSizeModifier = if (minTouchTargetSize > ChipDefaults.MinHeight) {
        Modifier.padding(vertical = (minTouchTargetSize - ChipDefaults.MinHeight) / 2f)
    } else {
        Modifier
    }
    val contentColor by colors.contentColor(enabled, checked)
    Surface(
        modifier = modifier.then(minTouchTargetSizeModifier),
        shape = shape,
        color = colors.backgroundColor(enabled, checked).value,
        contentColor = contentColor.copy(alpha = 1f),
        border = border,
        elevation = elevation.elevation(enabled, interactionSource).value,
        onClick = { onCheckedChange(checked.not()) },
        enabled = enabled,
        role = Role.Button,
        interactionSource = interactionSource,
        indication = rememberRipple(color = rippleColor)
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
    fun backgroundColor(enabled: Boolean, checked: Boolean): State<Color>

    @Composable
    fun contentColor(enabled: Boolean, checked: Boolean): State<Color>
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
    fun entryChipColors(
        checkedBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.18f)
            .compositeOver(MaterialTheme.colors.surface),
        checkedContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        uncheckedBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.10f)
            .compositeOver(MaterialTheme.colors.surface),
        uncheckedContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.33f)
    ): ChipColors = DefaultChipColors(
        checkedBackgroundColor = checkedBackgroundColor,
        checkedContentColor = checkedContentColor,
        uncheckedBackgroundColor = uncheckedBackgroundColor,
        uncheckedContentColor = uncheckedContentColor,
        disabledCheckedBackgroundColor = disabledBackgroundColor,
        disabledCheckedContentColor = disabledContentColor,
        disabledUncheckedBackgroundColor = disabledBackgroundColor,
        disabledUncheckedContentColor = disabledContentColor
    )

    @Composable
    fun filterChipColors(
        checkedBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.18f)
            .compositeOver(MaterialTheme.colors.surface),
        checkedContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        uncheckedBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.10f)
            .compositeOver(MaterialTheme.colors.surface),
        uncheckedContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.33f)
    ): ChipColors = DefaultChipColors(
        checkedBackgroundColor = checkedBackgroundColor,
        checkedContentColor = checkedContentColor,
        uncheckedBackgroundColor = uncheckedBackgroundColor,
        uncheckedContentColor = uncheckedContentColor,
        disabledCheckedBackgroundColor = disabledBackgroundColor,
        disabledCheckedContentColor = disabledContentColor,
        disabledUncheckedBackgroundColor = disabledBackgroundColor,
        disabledUncheckedContentColor = disabledContentColor
    )

    @Composable
    fun choiceChipColors(
        checkedBackgroundColor: Color = MaterialTheme.colors.primary.copy(alpha = 0.24f)
            .compositeOver(MaterialTheme.colors.surface),
        checkedContentColor: Color = MaterialTheme.colors.primary,
        uncheckedBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.10f)
            .compositeOver(MaterialTheme.colors.surface),
        uncheckedContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.33f)
    ): ChipColors = DefaultChipColors(
        checkedBackgroundColor = checkedBackgroundColor,
        checkedContentColor = checkedContentColor,
        uncheckedBackgroundColor = uncheckedBackgroundColor,
        uncheckedContentColor = uncheckedContentColor,
        disabledCheckedBackgroundColor = disabledBackgroundColor,
        disabledCheckedContentColor = disabledContentColor,
        disabledUncheckedBackgroundColor = disabledBackgroundColor,
        disabledUncheckedContentColor = disabledContentColor
    )

    @Composable
    fun actionChipColors(
        backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.10f)
            .compositeOver(MaterialTheme.colors.surface),
        contentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.87f),
        disabledBackgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            .compositeOver(MaterialTheme.colors.surface),
        disabledContentColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.33f)
    ): ChipColors = DefaultChipColors(
        checkedBackgroundColor = backgroundColor,
        checkedContentColor = contentColor,
        uncheckedBackgroundColor = backgroundColor,
        uncheckedContentColor = contentColor,
        disabledCheckedBackgroundColor = disabledBackgroundColor,
        disabledCheckedContentColor = disabledContentColor,
        disabledUncheckedBackgroundColor = disabledBackgroundColor,
        disabledUncheckedContentColor = disabledContentColor
    )
}

/**
 * Default [ChipColors] implementation.
 */
@Immutable
private class DefaultChipColors(
    private val checkedBackgroundColor: Color,
    private val checkedContentColor: Color,
    private val uncheckedBackgroundColor: Color,
    private val uncheckedContentColor: Color,
    private val disabledCheckedBackgroundColor: Color,
    private val disabledCheckedContentColor: Color,
    private val disabledUncheckedBackgroundColor: Color,
    private val disabledUncheckedContentColor: Color
) : ChipColors {

    @Composable
    override fun backgroundColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedBackgroundColor else uncheckedBackgroundColor
            } else {
                if (checked) disabledCheckedBackgroundColor else disabledUncheckedBackgroundColor
            }
        )
    }

    @Composable
    override fun contentColor(enabled: Boolean, checked: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (checked) checkedContentColor else uncheckedContentColor
            } else {
                if (checked) disabledCheckedContentColor else disabledUncheckedContentColor
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DefaultChipColors

        if (checkedBackgroundColor != other.checkedBackgroundColor) return false
        if (checkedContentColor != other.checkedContentColor) return false
        if (uncheckedBackgroundColor != other.uncheckedBackgroundColor) return false
        if (uncheckedContentColor != other.uncheckedContentColor) return false
        if (disabledCheckedBackgroundColor != other.disabledCheckedBackgroundColor) return false
        if (disabledCheckedContentColor != other.disabledCheckedContentColor) return false
        if (disabledUncheckedBackgroundColor != other.disabledUncheckedBackgroundColor) return false
        if (disabledUncheckedContentColor != other.disabledUncheckedContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = checkedBackgroundColor.hashCode()
        result = 31 * result + checkedContentColor.hashCode()
        result = 31 * result + uncheckedBackgroundColor.hashCode()
        result = 31 * result + uncheckedContentColor.hashCode()
        result = 31 * result + disabledCheckedBackgroundColor.hashCode()
        result = 31 * result + disabledCheckedContentColor.hashCode()
        result = 31 * result + disabledUncheckedBackgroundColor.hashCode()
        result = 31 * result + disabledUncheckedContentColor.hashCode()
        return result
    }
}
