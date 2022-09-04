package com.paktalin.petfinder.ui.pet

import android.widget.ImageView
import coil.load
import com.paktalin.petfinder.R

fun ImageView.loadPetPicture(url: String?) {
    load(url) {
        placeholder(R.drawable.ic_paw)
        fallback(R.drawable.ic_paw)
        error(R.drawable.ic_paw)
    }
}
