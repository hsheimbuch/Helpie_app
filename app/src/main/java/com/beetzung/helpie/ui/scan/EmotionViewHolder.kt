package com.beetzung.helpie.ui.scan

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.beetzung.helpie.R
import com.beetzung.helpie.core.getEmotionColor
import com.beetzung.helpie.core.getEmotionDrawable
import com.beetzung.helpie.core.getEmotionText
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
        binding.updateView(emotion, isSelected)
    }

    private fun RecyclerItemEmotionBinding.updateView(emotion: Emotion, isSelected: Boolean) {
        emotionText.text = getEmotionText(emotion)
        emotionBackground.background.setTint(getEmotionColor(emotion))
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
}