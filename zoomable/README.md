# Zoomable

<a href="https://jitpack.io/#fornewid/metronome"><img src="https://jitpack.io/v/fornewid/metronome.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.metronome:zoomable:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/metronome/releases) of the library.

## Usage

```kotlin
@Composable
fun ZoomableBoxSample() {
    Text(
        text = "Invisible Text",
        modifier = Modifier.visible(visible = false)
    )
}
```
