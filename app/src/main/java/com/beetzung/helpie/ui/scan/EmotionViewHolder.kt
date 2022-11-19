package com.beetzung.helpie.ui.scan

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.beetzung.helpie.R
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.databinding.RecyclerItemEmotionBinding

class EmotionViewHolder(
    private val binding: RecyclerItemEmotionBinding,
    private val clickListener: (Emotion) -> Unit
) :
    RecyclerView.ViewHolder((binding.root)) {

    var emotion: Emotion = Emotion.ANGRY
        private set

    var isSelected: Boolean = false
        private set

    init {
        binding.root.setOnClickListener {
            clickListener.invoke(emotion)
        }
    }

    fun bind(emotion: Emotion, isSelected: Boolean) {
        this.emotion = emotion
        binding.emotionText.text = getEmotionText(emotion)
        binding.emotionBackground.background.setTint(getEmotionColor(emotion))
        getEmotionDrawable(emotion)?.let(binding.emotionEmoji::setImageDrawable)
        setSelected(isSelected)
    }

    fun setSelected(selected: Boolean) {
        binding.emotionText.setTextColor(getTextColor(selected))
        binding.emotionShadow.visibility = if (selected) View.GONE else View.VISIBLE
        binding.emotionText.typeface = getTypeFace(isSelected)
        binding.root.invalidate()
        isSelected = selected
    }

    private fun getTypeFace(isSelected: Boolean) = ResourcesCompat.getFont(
        binding.root.context,
        if (isSelected) R.font.roboto_medium else R.font.roboto_regular
    )

    private fun getTextColor(isSelected: Boolean) = if (isSelected) {
        ContextCompat.getColor(binding.root.context, R.color.white)
    } else {
        ContextCompat.getColor(binding.root.context, R.color.white50)
    }

    private fun getEmotionColor(emotion: Emotion): Int {
        return when (emotion) {
            Emotion.HAPPY -> R.color.emotion_happy
            Emotion.SAD -> R.color.emotion_sad
            Emotion.ANGRY -> R.color.emotion_angry
            Emotion.NEUTRAL -> R.color.emotion_neutral
            Emotion.FEAR -> R.color.emotion_fear
            Emotion.DISGUST -> R.color.emotion_disgust
            Emotion.SURPRISE -> R.color.emotion_surprise
        }.let { ContextCompat.getColor(binding.root.context, it) }
    }

    private fun getEmotionDrawable(emotion: Emotion): Drawable? {
        return when (emotion) {
            Emotion.HAPPY -> R.drawable.emoji_happy
            Emotion.SAD -> R.drawable.emoji_sad
            Emotion.ANGRY -> R.drawable.emoji_angry
            Emotion.NEUTRAL -> R.drawable.emoji_happy //TODO change to neutral
            Emotion.FEAR -> R.drawable.emoji_fear
            Emotion.DISGUST -> R.drawable.emoji_disgust
            Emotion.SURPRISE -> R.drawable.emoji_fear //TODO change to surprise
        }.let { ContextCompat.getDrawable(binding.root.context, it) }
    }

    private fun getEmotionText(emotion: Emotion) = when (emotion) {
        Emotion.HAPPY -> "Happy"
        Emotion.SAD -> "Sad"
        Emotion.ANGRY -> "Angry"
        Emotion.NEUTRAL -> "Neutral"
        Emotion.FEAR -> "Fear"
        Emotion.DISGUST -> "Disgust"
        Emotion.SURPRISE -> "Surprise"
    }
}