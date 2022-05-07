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

import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult

private const val DebugLog = true
private const val Tag = "ReadMoreState"

@Stable
internal class ReadMoreState(
    private val originalText: AnnotatedString,
    private val readMoreMaxLines: Int
) {
    private var textLayout: TextLayoutResult? = null
    private var overflowTextLayout: TextLayoutResult? = null
    private var readMoreTextLayout: TextLayoutResult? = null

    private var _collapsedText: AnnotatedString by mutableStateOf(AnnotatedString(""))

    var collapsedText: AnnotatedString
        get() = _collapsedText
        internal set(value) {
            if (value != _collapsedText) {
                _collapsedText = value
                if (DebugLog) {
                    Log.d(Tag, "collapsedText changed: $_collapsedText")
                }
            }
        }

    fun onTextLayout(result: TextLayoutResult) {
        val lastLineIndex = readMoreMaxLines - 1
        val previous = textLayout
        val old = previous != null &&
            previous.lineCount <= readMoreMaxLines &&
            previous.isLineEllipsized(lastLineIndex)
        val new = result.lineCount <= readMoreMaxLines &&
            result.isLineEllipsized(lastLineIndex)
        val changed = previous != result && old != new
        if (changed) {
            if (DebugLog) {
                Log.d(Tag, "onTextLayout:")
            }
            textLayout = result
            updateCollapsedText()
        }
    }

    fun onOverflowTextLayout(result: TextLayoutResult) {
        val changed = overflowTextLayout?.size?.width != result.size.width
        if (changed) {
            if (DebugLog) {
                Log.d(Tag, "onOverflowTextLayout:")
            }
            overflowTextLayout = result
            updateCollapsedText()
        }
    }

    fun onReadMoreTextLayout(result: TextLayoutResult) {
        val changed = readMoreTextLayout?.size?.width != result.size.width
        if (changed) {
            if (DebugLog) {
                Log.d(Tag, "onReadMoreTextLayout:")
            }
            readMoreTextLayout = result
            updateCollapsedText()
        }
    }

    private fun updateCollapsedText() {
        val lastLineIndex = readMoreMaxLines - 1
        val textLayout = textLayout
        val overflowTextLayout = overflowTextLayout
        val readMoreTextLayout = readMoreTextLayout
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
            collapsedText = originalText.subSequence(startIndex = 0, endIndex = replacedEndIndex)
            if (DebugLog) {
                Log.d(Tag, "updateCollapsedText: collapsedText=$collapsedText")
            }
        }
    }

    override fun toString(): String {
        return "ReadMoreState(" +
            "originalText=$originalText, " +
            "readMoreMaxLines=$readMoreMaxLines, " +
            "collapsedText=$collapsedText" +
            ")"
    }
}
