/*
 * Copyright 2022 SOUP
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
package soup.metronome.sample.readmore

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import kotlin.text.Typography.ellipsis
import kotlin.text.Typography.nbsp

@Composable
fun ReadMoreText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    indication: Indication? = LocalIndication.current,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    readMoreText: String = "",
    readMoreMaxLines: Int = 2,
    readMoreStyle: SpanStyle = style.toSpanStyle()
) {
    require(readMoreMaxLines > 0) { "readMoreMaxLines should be greater than 0" }

    val overflowText: String = remember(overflow) {
        buildString {
            if (overflow == TextOverflow.Ellipsis) {
                append(ellipsis)
            }
            if (readMoreText.isNotEmpty()) {
                append(nbsp)
            }
        }
    }
    val readMoreTextWithStyle: AnnotatedString = remember(readMoreText, readMoreStyle) {
        buildAnnotatedString {
            if (readMoreText.isNotEmpty()) {
                withStyle(readMoreStyle) {
                    append(readMoreText.replace(' ', nbsp))
                }
            }
        }
    }

    var collapsedText by remember(text) { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val (textLayout, onCurrentTextLayout) = remember { mutableStateOf<TextLayoutResult?>(null) }
    val (overflowTextLayout, onOverflowTextLayout) = remember { mutableStateOf<TextLayoutResult?>(null) }
    val (readMoreTextLayout, onReadMoreTextLayout) = remember { mutableStateOf<TextLayoutResult?>(null) }

    LaunchedEffect(text, textLayout, overflowTextLayout, readMoreTextLayout) {
        val lastLineIndex = readMoreMaxLines - 1
        if (textLayout != null &&
            overflowTextLayout != null &&
            readMoreTextLayout != null &&
            textLayout.lineCount <= readMoreMaxLines &&
            textLayout.isLineEllipsized(lastLineIndex)
        ) {
            val countUntilMaxLine = textLayout.getLineEnd(readMoreMaxLines - 1, visibleEnd = true)
            val readMoreWidth = overflowTextLayout.size.width + readMoreTextLayout.size.width
            val maximumWidth = textLayout.size.width - readMoreWidth
            var replacedEndIndex = countUntilMaxLine + 1
            var currentTextBounds: Rect
            do {
                replacedEndIndex -= 1
                currentTextBounds = textLayout.getCursorRect(replacedEndIndex)
            } while (currentTextBounds.left > maximumWidth)
            collapsedText = text.substring(startIndex = 0, endIndex = replacedEndIndex)
        }
    }

    val currentText = buildAnnotatedString {
        if (expanded.not() && collapsedText.isNotEmpty()) {
            append(collapsedText)
            append(overflowText)
            append(readMoreTextWithStyle)
        } else {
            append(text)
        }
    }
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = indication,
                enabled = enabled,
                onClickLabel = onClickLabel,
                role = role,
                onClick = {
                    expanded = !expanded
                }
            )
            .then(modifier)
    ) {
        Text(
            text = currentText,
            modifier = Modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = TextOverflow.Ellipsis,
            softWrap = softWrap,
            maxLines = if (expanded) Int.MAX_VALUE else readMoreMaxLines,
            onTextLayout = {
                onCurrentTextLayout(it)
                onTextLayout(it)
            },
            style = style
        )
        if (expanded.not()) {
            Text(
                text = overflowText,
                onTextLayout = onOverflowTextLayout,
                modifier = Modifier.notDraw(),
                style = style
            )
            Text(
                text = readMoreTextWithStyle,
                onTextLayout = onReadMoreTextLayout,
                modifier = Modifier.notDraw(),
                style = style.merge(readMoreStyle)
            )
        }
    }
}

private fun Modifier.notDraw(): Modifier {
    return then(NotDrawModifier)
}

private object NotDrawModifier : DrawModifier {

    override fun ContentDrawScope.draw() {
        // not draws content.
    }
}
