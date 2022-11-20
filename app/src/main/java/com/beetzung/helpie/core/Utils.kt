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
import com.beetzung.helpie.data.model.PreEmotion
import com.beetzung.helpie.ui.BaseFragment

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

fun ViewBinding.getEmotionColor(emotion: PreEmotion): Int {
    return when (emotion) {
        PreEmotion.POSITIVE -> R.color.emotion_happy
        PreEmotion.SAD -> R.color.emotion_sad
        PreEmotion.ANGRY -> R.color.emotion_angry
        PreEmotion.FEAR -> R.color.emotion_fear
        PreEmotion.DISGUST -> R.color.emotion_disgust
    }.let { ContextCompat.getColor(root.context, it) }
}

fun ViewBinding.getEmotionDrawable(emotion: PreEmotion): Drawable? {
    return when (emotion) {
        PreEmotion.SAD -> R.drawable.emoji_sad
        PreEmotion.ANGRY -> R.drawable.emoji_angry
        PreEmotion.POSITIVE -> R.drawable.emoji_happy
        PreEmotion.FEAR -> R.drawable.emoji_fear
        PreEmotion.DISGUST -> R.drawable.emoji_disgust
    }.let { ContextCompat.getDrawable(root.context, it) }
}

fun ViewBinding.getEmotionText(emotion: PreEmotion) = when (emotion) {
    PreEmotion.POSITIVE -> R.string.text_positive
    PreEmotion.ANGRY -> R.string.text_angry
    PreEmotion.DISGUST -> R.string.text_disgust
    PreEmotion.FEAR -> R.string.text_fear
    PreEmotion.SAD -> R.string.text_sad
}.let { root.context.getString(it) }
