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
package soup.metronome.sample

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import soup.compose.material.motion.navigation.MaterialMotionNavHost
import soup.compose.material.motion.navigation.composable
import soup.compose.material.motion.navigation.rememberMaterialMotionNavController
import soup.metronome.sample.material.ChipDemo
import soup.metronome.sample.material.UnelevatedButtonDemo
import soup.metronome.sample.readmore.material.Material_ReadMoreTextDemo
import soup.metronome.sample.readmore.material3.Material3_ReadMoreTextDemo
import soup.metronome.sample.visibility.VisibilityDemo

data class Destination(
    val key: String,
    val description: String,
    val content: @Composable (NavBackStackEntry) -> Unit
) {
    companion object {
        val all: List<Destination> = listOf(
            Destination("material:Chip", "ChipDemo") {
                ChipDemo()
            },
            Destination("material:UnelevatedButton", "UnelevatedButtonDemo") {
                UnelevatedButtonDemo()
            },
            Destination("readmore-material:ReadMoreText", "ReadMoreTextDemo") {
                Material_ReadMoreTextDemo()
            },
            Destination("readmore-material3:ReadMoreText", "ReadMoreTextDemo") {
                Material3_ReadMoreTextDemo()
            },
            Destination("visibility:Visibility", "VisibilityDemo") {
                VisibilityDemo()
            },
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph() {
    val destinations = Destination.all
    val navController = rememberMaterialMotionNavController()
    MaterialMotionNavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                destinations,
                onItemClick = {
                    navController.navigate(it.key)
                }
            )
        }

        destinations.forEach { destination ->
            composable(destination.key) {
                destination.content(it)
            }
        }
    }
}
