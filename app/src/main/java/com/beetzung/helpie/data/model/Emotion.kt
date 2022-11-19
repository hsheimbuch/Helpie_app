package com.beetzung.helpie.data.model

enum class Emotion {
    ANGRY,
    DISGUST,
    FEAR,
    HAPPY,
    SAD,
    SURPRISE,
    NEUTRAL
}

fun fromNumber(int: Int) = when (int) {
    0 -> Emotion.ANGRY
    1 -> Emotion.DISGUST
    2 -> Emotion.FEAR
    3 -> Emotion.HAPPY
    4 -> Emotion.SAD
    5 -> Emotion.SURPRISE
    else -> Emotion.NEUTRAL
}
