package com.beetzung.helpie.ui.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.data.model.fromNumber
import com.beetzung.helpie.databinding.RecyclerItemEmotionBinding

class EmotionAdapter(
    private val initialEmotion: Emotion
) : RecyclerView.Adapter<EmotionViewHolder>() {

    private val elements = mutableMapOf<Emotion, EmotionViewHolder>()
    private var firstSelectionInitialized: Boolean = false

    var selectedEmotion: Emotion = initialEmotion
        private set

    private val clickListener: (Emotion) -> Unit = { emotion ->
        elements.values.forEach { it.setSelected(false) }
        elements[emotion]?.setSelected(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmotionViewHolder(
        RecyclerItemEmotionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        clickListener
    )

    override fun onBindViewHolder(holder: EmotionViewHolder, position: Int) {
        val emotion = fromNumber(position)
        val isSelected = elements[emotion]?.isSelected ?: (selectedEmotion == emotion)
        holder.bind(emotion, isSelected)
        elements[emotion] = holder
        if (!firstSelectionInitialized && emotion == initialEmotion) {
            elements[emotion]?.setSelected(true)
            firstSelectionInitialized = true
        }
    }

    override fun getItemCount() = Emotion.values().size


}