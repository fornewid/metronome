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
package soup.metronome.sample.zoomable

import android.content.ContentResolver
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import kotlinx.coroutines.launch
import soup.metronome.sample.R
import soup.metronome.sample.theme.SampleTheme
import soup.metronome.zoomable.ZoomableBox
import soup.metronome.zoomable.ZoomableState
import soup.metronome.zoomable.rememberZoomableState

@Composable
fun ZoomableBoxDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "ZoomableBoxDemo") }) }
    ) {
        BoxWithConstraints(modifier = Modifier.padding(it)) {
            val zoomableStateWithDrawable: ZoomableState = rememberZoomableState()
            val zoomableStateWithCoil: ZoomableState = rememberZoomableState()
            if (maxWidth < maxHeight) {
                Column {
                    ZoomableBoxWithDrawable(
                        zoomableState = zoomableStateWithDrawable,
                        modifier = Modifier.weight(1f)
                    )
                    Divider()
                    ZoomableBoxWithCoil(
                        zoomableState = zoomableStateWithCoil,
                        modifier = Modifier.weight(1f)
                    )
                }
            } else {
                Row {
                    ZoomableBoxWithDrawable(
                        zoomableState = zoomableStateWithDrawable,
                        modifier = Modifier.weight(1f)
                    )
                    Divider(modifier = Modifier.width(1.dp).fillMaxHeight())
                    ZoomableBoxWithCoil(
                        zoomableState = zoomableStateWithCoil,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ZoomableBoxWithDrawable(
    zoomableState: ZoomableState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = zoomableState.isScaled) {
        coroutineScope.launch {
            zoomableState.animateToMinimum()
        }
    }
    val painter = painterResource(R.drawable.wallpaper)
    zoomableState.imageSize = painter.intrinsicSize
    ZoomableBox(
        modifier = modifier,
        state = zoomableState,
    ) {
        Image(
            painter,
            contentDescription = "drawable",
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun ZoomableBoxWithCoil(
    zoomableState: ZoomableState,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = zoomableState.isScaled) {
        coroutineScope.launch {
            zoomableState.animateToMinimum()
        }
    }
    ZoomableBox(
        modifier = modifier,
        state = zoomableState,
    ) {
        AsyncImage(
            uriOf(R.drawable.wallpaper),
            contentDescription = "coil",
            modifier = Modifier.fillMaxSize(),
            transform = {
                if (it is AsyncImagePainter.State.Success) {
                    zoomableState.imageSize = it.painter.intrinsicSize
                }
                it
            }
        )
    }
}

@Composable
private fun uriOf(@DrawableRes resId: Int): Uri {
    val resources = LocalContext.current.resources
    return Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resId))
        .appendPath(resources.getResourceTypeName(resId))
        .appendPath(resources.getResourceEntryName(resId))
        .build()
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    SampleTheme {
        ZoomableBoxDemo()
    }
}
