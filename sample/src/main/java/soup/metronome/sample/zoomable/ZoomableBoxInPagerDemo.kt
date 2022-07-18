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
package soup.metronome.sample.zoomable

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import soup.metronome.sample.R
import soup.metronome.sample.theme.SampleTheme
import soup.metronome.zoomable.ExperimentalZoomableApi
import soup.metronome.zoomable.ZoomableBox
import soup.metronome.zoomable.rememberZoomableState

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalZoomableApi::class,
)
@Composable
fun ZoomableBoxInPagerDemo() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "ZoomableBoxWithPagerDemo") })
        }
    ) {
        val coroutineScope = rememberCoroutineScope()
        val pageCount = 5
        val startIndex = Int.MAX_VALUE / 2
        val pagerState = rememberPagerState(initialPage = startIndex)

        HorizontalPager(
            count = Int.MAX_VALUE,
            state = pagerState,
            contentPadding = it,
        ) { index ->
            val page = (index - startIndex).floorMod(pageCount)
            Box(contentAlignment = Alignment.Center) {
                val zoomableState = rememberZoomableState()
                val painter = painterResource(R.drawable.wallpaper)
                zoomableState.contentIntrinsicSize = painter.intrinsicSize
                ZoomableBox(state = zoomableState) {
                    Image(
                        painter,
                        contentDescription = "image $page",
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Text(
                    text = "Page $page",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )

                BackHandler(enabled = pagerState.currentPage == index && zoomableState.isScaled) {
                    coroutineScope.launch {
                        zoomableState.animateToInitialState()
                    }
                }
            }
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    SampleTheme {
        ZoomableBoxInPagerDemo()
    }
}
