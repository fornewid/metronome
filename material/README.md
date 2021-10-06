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

#### [Chip](https://github.com/fornewid/compose-experimental/blob/main/material/src/main/java/soup/compose/material/chip/Chip.kt)

```kotlin
@Composable
fun ChipSample() {
    Chip(onClick = { /* Do something! */ }) {
        Text(text = "Chip")
    }
}

@Composable
fun FilterChipSample() {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    FilterChip(
        checked = checked,
        onCheckedChange = onCheckedChange,
        onCloseIconClick = { /* Do something! */ }
    ) {
        Text(text = "Like")
    }
}
```

#### [UnelevatedButton](https://github.com/fornewid/compose-experimental/blob/main/material/src/main/java/soup/compose/material/UnelevatedButton.kt)

```kotlin
@Composable
fun UnelevatedButtonSample() {
    UnelevatedButton(onClick = { /* Do something! */ }) {
        Text(text = "UnelevatedButton")
    }
}
```
