# Visibility

<a href="https://jitpack.io/#fornewid/metronome"><img src="https://jitpack.io/v/fornewid/metronome.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.metronome:visibility:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/metronome/releases) of the library.

## Usage

```kotlin
@Composable
fun VisibleSample() {
    Text(
        text = "Invisible Text",
        modifier = Modifier.visible(visible = false)
    )
}

@Composable
fun InvisibleSample() {
    Text(
        text = "Invisible Text",
        modifier = Modifier.invisible(invisible = true)
    )
}
```
