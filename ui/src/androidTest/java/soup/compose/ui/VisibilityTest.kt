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
package soup.compose.ui

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import soup.compose.experimental.internal.test.assertPixelOfCenter

@RunWith(AndroidJUnit4::class)
class VisibilityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val outerTag = "outer"
    private val innerTag = "inner"

    @Before
    fun before() {
        isDebugInspectorInfoEnabled = true
    }

    @After
    fun after() {
        isDebugInspectorInfoEnabled = false
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.O) // captureToImage is SDK 26+
    fun visibility_switchVisibilityState() {
        var visibilityState by mutableStateOf(VisibilityState.Visible)

        composeTestRule.setContent {
            // Red color is displayed only when Green Box is Invisible.
            Column(
                modifier = Modifier
                    .size(20.dp)
                    .background(color = Color.Red)
                    .testTag(outerTag)
            ) {

                // Green Box is displayed on Visible only.
                Visibility(visibility = visibilityState) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Color.Green)
                            .testTag(innerTag)
                    )
                }

                // Blue Box is displayed only when Green Box is Gone.
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color = Color.Blue)
                )
            }
        }

        composeTestRule.onNodeWithTag(outerTag)
            .captureToImage()
            .assertPixelOfCenter(Color.Green)

        composeTestRule.onNodeWithTag(innerTag)
            .captureToImage()
            .assertPixelOfCenter(Color.Green)

        visibilityState = VisibilityState.Invisible

        composeTestRule.onNodeWithTag(outerTag)
            .captureToImage()
            .assertPixelOfCenter(Color.Red)

        composeTestRule.onNodeWithTag(innerTag)
            .captureToImage()
            .assertPixelOfCenter(Color.Red)

        visibilityState = VisibilityState.Gone

        composeTestRule.onNodeWithTag(outerTag)
            .captureToImage()
            .assertPixelOfCenter(Color.Blue)

        composeTestRule.onNodeWithTag(innerTag)
            .assertDoesNotExist()
    }
}
