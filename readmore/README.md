# BasicReadMoreText

<a href="https://jitpack.io/#fornewid/metronome"><img src="https://jitpack.io/v/fornewid/metronome.svg"/></a>

## Installation

```gradle
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.fornewid.metronome:readmore:<version>"
}
```

Depend on the [latest version](https://github.com/fornewid/metronome/releases) of the library.

## Usage

#### [BasicReadMoreText](https://github.com/fornewid/metronome/blob/main/readmore/src/main/java/soup/metronome/readmore/BasicReadMoreText.kt)

`BasicReadMoreText` is a basic element like `androidx.compose.foundation.text.BasicText`.

```kotlin
@Composable
fun BasicReadMoreTextSample() {
    val (expanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }
    BasicReadMoreText(
        text = description,
        expanded = expanded,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 5.dp, end = 18.dp, bottom = 18.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 100)),
        readMoreText = "Read more",
        readMoreMaxLines = 2,
        readMoreStyle = SpanStyle(
            color = Color.Black,
            fontSize = 15.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Default,
            textDecoration = TextDecoration.None
        )
    )
}
```
