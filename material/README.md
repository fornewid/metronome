# material

<a href="https://jitpack.io/#fornewid/compose-experimental"><img src="https://jitpack.io/v/fornewid/compose-experimental.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.compose-experimental:material:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/compose-experimental/releases) of the library.

## Usage

#### [UnelevatedButton](https://github.com/fornewid/compose-experimental/blob/main/material/src/main/java/soup/compose/material/UnelevatedButton.kt)

```kotlin
@Composable
fun UnelevatedButtonSample() {
    UnelevatedButton(onClick = { /* Do something! */ }) {
        Text(text = "UnelevatedButton")
    }
}
```
