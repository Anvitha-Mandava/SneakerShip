package com.example.sneakership.adapters

import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.sneakership.R
import com.squareup.picasso.Picasso

@BindingAdapter("loadImageUrl")
fun ImageView.loadImageUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        Picasso.get().load(url).error(R.drawable.ic_error_24).into(this)
    } else {
        this.setImageResource(R.drawable.ic_error_24)
    }
}

@BindingAdapter("selectedIcon")
fun setSelectedIcon(imageView: ImageView, isSelected: Boolean) {
    val iconResId = when {
        isSelected -> R.drawable.ic_home_white
        else -> R.drawable.ic_cart_28
    }
    imageView.setImageResource(iconResId)

    val colorResId = when {
        isSelected -> android.R.color.white
        else -> R.color.app_color
    }
    imageView.setColorFilter(
        ContextCompat.getColor(imageView.context, colorResId), PorterDuff.Mode.SRC_IN
    )
}

@BindingAdapter("selectedBackground")
fun setSelectedBackground(view: View, isSelected: Boolean) {
    val backgroundColorResId = if (isSelected) {
        R.color.app_color
    } else {
        android.R.color.white
    }
    view.setBackgroundResource(backgroundColorResId)
}