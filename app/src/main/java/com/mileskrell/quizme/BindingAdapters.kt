package com.mileskrell.quizme

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("numCorrect")
fun TextView.setNumCorrect(num: Int?) {
    num?.let {
        text = resources.getString(R.string.correct, num)
    }
}
