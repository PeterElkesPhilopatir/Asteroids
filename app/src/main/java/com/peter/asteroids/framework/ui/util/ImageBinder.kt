package com.peter.asteroids.framework.ui.util

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.peter.asteroids.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String) {
    if (imgUrl.isNotEmpty()) {
        try {
            imgUrl.let {
                imgView.clipToOutline = true
                var imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(imgView.context)
                    .load(imgUri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.loading_animation)
                    )
                    .into(imgView)
            }
        } catch (e: Exception) {
        }
    }
}

@BindingAdapter("asteroidStatus")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}