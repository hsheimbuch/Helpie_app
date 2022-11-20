package com.beetzung.helpie.data.model

import androidx.annotation.Keep

@Keep
enum class Emotion {
    WORRY,
    ANXIETY,
    PANIC,
    MELANCHOLY,
    SADNESS,
    GRIEF,
    ANNOYANCE,
    ANGER,
    RAGE,
    SHAME,
    GUILT,
    DISGUST,
    NEUTRAL,
    SURPRISED,
    HAPPY;

    fun toPreEmotion(): PreEmotion {
        return when (this) {
            WORRY, ANXIETY, PANIC -> PreEmotion.FEAR
            MELANCHOLY, SADNESS, GRIEF -> PreEmotion.SAD
            ANNOYANCE, ANGER, RAGE -> PreEmotion.ANGRY
            DISGUST, SHAME, GUILT -> PreEmotion.DISGUST
            NEUTRAL, SURPRISED, HAPPY -> PreEmotion.POSITIVE
        }
    }
}

@Keep
enum class ApiEmotion {
    ANGRY,
    DISGUST,
    FEAR,
    HAPPY,
    SAD,
    SURPRISE,
    NEUTRAL;

    fun toPreEmotion(): PreEmotion = when (this) {
        ANGRY -> PreEmotion.ANGRY
        DISGUST -> PreEmotion.DISGUST
        FEAR -> PreEmotion.FEAR
        SAD -> PreEmotion.SAD
        SURPRISE, NEUTRAL, HAPPY -> PreEmotion.POSITIVE
    }
}

@Keep
enum class PreEmotion {
    FEAR,
    SAD,
    ANGRY,
    DISGUST,
    POSITIVE
}

fun emotionFromNumber(int: Int) = when (int) {
    0 -> Emotion.WORRY
    1 -> Emotion.ANXIETY
    2 -> Emotion.PANIC
    3 -> Emotion.MELANCHOLY
    4 -> Emotion.SADNESS
    5 -> Emotion.GRIEF
    6 -> Emotion.ANNOYANCE
    7 -> Emotion.ANGER
    8 -> Emotion.RAGE
    9 -> Emotion.SHAME
    10 -> Emotion.GUILT
    11 -> Emotion.DISGUST
    12 -> Emotion.NEUTRAL
    13 -> Emotion.SURPRISED
    else -> Emotion.HAPPY
}

fun apiEmotionFromNumber(int: Int) = when (int) {
    0 -> ApiEmotion.ANGRY
    1 -> ApiEmotion.DISGUST
    2 -> ApiEmotion.FEAR
    3 -> ApiEmotion.HAPPY
    4 -> ApiEmotion.SAD
    5 -> ApiEmotion.SURPRISE
    6 -> ApiEmotion.NEUTRAL
    else -> null
}

fun preEmotionFromNumber(int: Int) = when (int) {
    0 -> PreEmotion.FEAR
    1 -> PreEmotion.SAD
    2 -> PreEmotion.ANGRY
    3 -> PreEmotion.DISGUST
    else -> PreEmotion.POSITIVE
}
