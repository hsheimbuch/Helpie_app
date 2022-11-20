package com.beetzung.helpie.core

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.ui.BaseFragment
import java.time.LocalDate
import java.time.YearMonth

val AppCompatActivity.currentFragment: BaseFragment?
    get() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.activity_nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0) as? BaseFragment
    }

val Any.TAG: String
    get() = "HELPIELOG" + this::class.java.simpleName

fun Fragment.pop() {
    navController.popBackStack()
}

val Fragment.navController: NavController
    get() = findNavController()

val AppCompatActivity.navController: NavController
    get() = (supportFragmentManager.findFragmentById(R.id.activity_nav_host_fragment) as NavHostFragment).navController

class DataEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun handle(handler: (T) -> Unit) {
        if (!hasBeenHandled) {
            hasBeenHandled = true
            handler(content)
        }
    }

    companion object {
        fun new(): Event = Event(Unit)
    }
}

typealias Event = DataEvent<Unit>

fun View.onDisplayed(callback: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            callback.invoke()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}

fun ViewBinding.getEmotionColor(emotion: Emotion): Int {
    return when (emotion) {
        Emotion.HAPPY -> R.color.emotion_happy
        Emotion.SAD -> R.color.emotion_sad
        Emotion.ANGRY -> R.color.emotion_angry
        Emotion.NEUTRAL -> R.color.emotion_neutral
        Emotion.FEAR -> R.color.emotion_fear
        Emotion.DISGUST -> R.color.emotion_disgust
        Emotion.SURPRISE -> R.color.emotion_surprise
    }.let { ContextCompat.getColor(root.context, it) }
}

fun ViewBinding.getEmotionDrawable(emotion: Emotion): Drawable? {
    return when (emotion) {
        Emotion.HAPPY -> R.drawable.emoji_happy
        Emotion.SAD -> R.drawable.emoji_sad
        Emotion.ANGRY -> R.drawable.emoji_angry
        Emotion.NEUTRAL -> R.drawable.emoji_happy //TODO change to neutral
        Emotion.FEAR -> R.drawable.emoji_fear
        Emotion.DISGUST -> R.drawable.emoji_disgust
        Emotion.SURPRISE -> R.drawable.emoji_fear //TODO change to surprise
    }.let { ContextCompat.getDrawable(root.context, it) }
}

fun ViewBinding.getEmotionText(emotion: Emotion) = when (emotion) {
    Emotion.HAPPY -> "Happy"
    Emotion.SAD -> "Sad"
    Emotion.ANGRY -> "Angry"
    Emotion.NEUTRAL -> "Neutral"
    Emotion.FEAR -> "Fear"
    Emotion.DISGUST -> "Disgust"
    Emotion.SURPRISE -> "Surprise"
}