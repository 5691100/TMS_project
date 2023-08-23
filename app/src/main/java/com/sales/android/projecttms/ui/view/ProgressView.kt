package com.sales.android.projecttms.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.sales.android.projecttms.databinding.ViewProgressBinding

class ProgressView(
    context: Context, attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    init {
        ViewProgressBinding.inflate(LayoutInflater.from(context), this, true)
    }
}