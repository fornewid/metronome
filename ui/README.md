# ui

<a href="https://jitpack.io/#fornewid/compose-experimental"><img src="https://jitpack.io/v/fornewid/compose-experimental.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.compose-experimental:ui:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/compose-experimental/releases) of the library.

## Usage

#### [Invisible](https://github.com/fornewid/compose-experimental/blob/main/ui/src/main/java/soup/compose/ui/Invisible.kt)

```kotlin
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InvisibleSample() {
    Invisible(invisible = true) {
        Text("Invisible Text")
    }
}
```
