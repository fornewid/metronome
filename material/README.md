# material

<a href="https://jitpack.io/#fornewid/metronome"><img src="https://jitpack.io/v/fornewid/metronome.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.metronome:material:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/metronome/releases) of the library.

## Usage

#### [Chip](https://github.com/fornewid/metronome/blob/main/material/src/main/java/soup/metronome/material/chip/Chip.kt)

```kotlin
@Composable
fun ActionChipSample() {
    ActionChip(
        text = "ActionChip",
        onClick = { /* Do something! */ }
    )
}

@Composable
fun EntryChipSample() {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    EntryChip(
        text = "EntryChip",
        checked = checked,
        onCheckedChange = onCheckedChange,
        onCloseIconClick = { /* Do something! */ }
    )
}

@Composable
fun FilterChipSample() {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    FilterChip(
        text = "FilterChip",
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun ChoiceChipSample() {
    val (checked, onCheckedChange) = remember { mutableStateOf(true) }
    ChoiceChip(
        text = "ChoiceChip",
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}
```

#### [UnelevatedButton](https://github.com/fornewid/metronome/blob/main/material/src/main/java/soup/metronome/material/UnelevatedButton.kt)

```kotlin
@Composable
fun UnelevatedButtonSample() {
    UnelevatedButton(onClick = { /* Do something! */ }) {
        Text(text = "UnelevatedButton")
    }
}
```
