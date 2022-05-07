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
package soup.metronome.readmore.material

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import soup.metronome.readmore.BasicReadMoreText

@Composable
fun ReadMoreText(
    text: String,
    expanded: Boolean,
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
    readMoreText: String = "",
    readMoreColor: Color = Color.Unspecified,
    readMoreFontSize: TextUnit = TextUnit.Unspecified,
    readMoreFontStyle: FontStyle? = null,
    readMoreFontWeight: FontWeight? = null,
    readMoreFontFamily: FontFamily? = null,
    readMoreTextDecoration: TextDecoration? = null,
    readMoreMaxLines: Int = 2,
    readMoreStyle: SpanStyle = style.toSpanStyle()
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }
    // NOTE(text-perf-review): It might be worthwhile writing a bespoke merge implementation that
    // will avoid reallocating if all of the options here are the defaults
    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        )
    )
    val mergedReadMoreStyle = mergedStyle.toSpanStyle()
        .merge(readMoreStyle)
        .merge(
            SpanStyle(
                color = readMoreColor,
                fontSize = readMoreFontSize,
                fontWeight = readMoreFontWeight,
                fontFamily = readMoreFontFamily,
                textDecoration = readMoreTextDecoration,
                fontStyle = readMoreFontStyle
            )
        )
    BasicReadMoreText(
        text = text,
        expanded = expanded,
        modifier = modifier,
        style = mergedStyle,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        readMoreText = readMoreText,
        readMoreMaxLines = readMoreMaxLines,
        readMoreStyle = mergedReadMoreStyle,
    )
}

@Composable
fun ReadMoreText(
    text: AnnotatedString,
    expanded: Boolean,
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
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    readMoreText: String = "",
    readMoreColor: Color = Color.Unspecified,
    readMoreFontSize: TextUnit = TextUnit.Unspecified,
    readMoreFontStyle: FontStyle? = null,
    readMoreFontWeight: FontWeight? = null,
    readMoreFontFamily: FontFamily? = null,
    readMoreTextDecoration: TextDecoration? = null,
    readMoreMaxLines: Int = 2,
    readMoreStyle: SpanStyle = style.toSpanStyle()
) {
    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }
    // NOTE(text-perf-review): It might be worthwhile writing a bespoke merge implementation that
    // will avoid reallocating if all of the options here are the defaults
    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        )
    )
    val mergedReadMoreStyle = mergedStyle.toSpanStyle()
        .merge(readMoreStyle)
        .merge(
            SpanStyle(
                color = readMoreColor,
                fontSize = readMoreFontSize,
                fontWeight = readMoreFontWeight,
                fontFamily = readMoreFontFamily,
                textDecoration = readMoreTextDecoration,
                fontStyle = readMoreFontStyle
            )
        )
    BasicReadMoreText(
        text = text,
        expanded = expanded,
        modifier = modifier,
        style = mergedStyle,
        onTextLayout = onTextLayout,
        overflow = overflow,
        softWrap = softWrap,
        inlineContent = inlineContent,
        readMoreText = readMoreText,
        readMoreMaxLines = readMoreMaxLines,
        readMoreStyle = mergedReadMoreStyle,
    )
}
