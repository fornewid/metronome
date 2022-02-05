# ui

<a href="https://jitpack.io/#fornewid/compose-extensions"><img src="https://jitpack.io/v/fornewid/compose-extensions.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.compose-extensions:ui:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/compose-extensions/releases) of the library.

## Usage

#### [Visibility](https://github.com/fornewid/compose-extensions/blob/main/ui/src/main/java/soup/compose/ui/Visibility.kt)

```kotlin
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VisibleSample() {
    Text(
        text = "Invisible Text",
        modifier = Modifier.visible(visible = false)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InvisibleSample() {
    Text(
        text = "Invisible Text",
        modifier = Modifier.invisible(invisible = true)
    )
}
```
