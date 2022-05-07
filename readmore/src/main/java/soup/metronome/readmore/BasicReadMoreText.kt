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
package soup.metronome.readmore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun BasicReadMoreText(
    text: String,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    readMoreText: String = "",
    readMoreMaxLines: Int = 2,
    readMoreStyle: SpanStyle = style.toSpanStyle()
) {
    require(readMoreMaxLines > 0) { "readMoreMaxLines should be greater than 0" }

    val overflowText: String = remember(overflow) {
        buildString {
            if (overflow == TextOverflow.Ellipsis) {
                append(Typography.ellipsis)
            }
            if (readMoreText.isNotEmpty()) {
                append(Typography.nbsp)
            }
        }
    }
    val readMoreTextWithStyle: AnnotatedString = remember(readMoreText, readMoreStyle) {
        buildAnnotatedString {
            if (readMoreText.isNotEmpty()) {
                withStyle(readMoreStyle) {
                    append(readMoreText.replace(' ', Typography.nbsp))
                }
            }
        }
    }

    val state = remember(text, readMoreMaxLines) {
        ReadMoreState(
            originalText = AnnotatedString(text),
            readMoreMaxLines = readMoreMaxLines
        )
    }
    val collapsedText = state.collapsedText
    val currentText = buildAnnotatedString {
        if (expanded.not() && collapsedText.isNotEmpty()) {
            append(collapsedText)
            append(overflowText)
            append(readMoreTextWithStyle)
        } else {
            append(text)
        }
    }
    Box(modifier = modifier) {
        BasicText(
            text = currentText,
            modifier = Modifier,
            style = style,
            onTextLayout = {
                state.onTextLayout(it)
                onTextLayout(it)
            },
            overflow = TextOverflow.Ellipsis,
            softWrap = softWrap,
            maxLines = if (expanded) Int.MAX_VALUE else readMoreMaxLines
        )
        if (expanded.not()) {
            BasicText(
                text = overflowText,
                onTextLayout = { state.onOverflowTextLayout(it) },
                modifier = Modifier.notDraw(),
                style = style
            )
            BasicText(
                text = readMoreTextWithStyle,
                onTextLayout = { state.onReadMoreTextLayout(it) },
                modifier = Modifier.notDraw(),
                style = style.merge(readMoreStyle)
            )
        }
    }
}

@Composable
fun BasicReadMoreText(
    text: AnnotatedString,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    readMoreText: String = "",
    readMoreMaxLines: Int = 2,
    readMoreStyle: SpanStyle = style.toSpanStyle()
) {
    require(readMoreMaxLines > 0) { "readMoreMaxLines should be greater than 0" }

    val overflowText: String = remember(overflow) {
        buildString {
            if (overflow == TextOverflow.Ellipsis) {
                append(Typography.ellipsis)
            }
            if (readMoreText.isNotEmpty()) {
                append(Typography.nbsp)
            }
        }
    }
    val readMoreTextWithStyle: AnnotatedString = remember(readMoreText, readMoreStyle) {
        buildAnnotatedString {
            if (readMoreText.isNotEmpty()) {
                withStyle(readMoreStyle) {
                    append(readMoreText.replace(' ', Typography.nbsp))
                }
            }
        }
    }

    val state = remember(text, readMoreMaxLines) {
        ReadMoreState(
            originalText = text,
            readMoreMaxLines = readMoreMaxLines
        )
    }
    val collapsedText = state.collapsedText
    val currentText = buildAnnotatedString {
        if (expanded.not() && collapsedText.isNotEmpty()) {
            append(collapsedText)
            append(overflowText)
            append(readMoreTextWithStyle)
        } else {
            append(text)
        }
    }
    Box(modifier = modifier) {
        BasicText(
            text = currentText,
            modifier = Modifier,
            style = style,
            onTextLayout = {
                state.onTextLayout(it)
                onTextLayout(it)
            },
            overflow = TextOverflow.Ellipsis,
            softWrap = softWrap,
            maxLines = if (expanded) Int.MAX_VALUE else readMoreMaxLines,
            inlineContent = inlineContent
        )
        if (expanded.not()) {
            BasicText(
                text = overflowText,
                onTextLayout = { state.onOverflowTextLayout(it) },
                modifier = Modifier.notDraw(),
                style = style
            )
            BasicText(
                text = readMoreTextWithStyle,
                onTextLayout = { state.onReadMoreTextLayout(it) },
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
