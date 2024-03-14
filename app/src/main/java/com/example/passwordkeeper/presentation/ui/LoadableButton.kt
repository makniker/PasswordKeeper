package com.example.passwordkeeper.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.passwordkeeper.R
import com.example.passwordkeeper.databinding.ViewLoadableButtonBinding

class LoadableButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private var binding: ViewLoadableButtonBinding? = null

    init {
        binding = ViewLoadableButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.view_loadable_button, this, true)
        )
    }

    fun setStateLoading() = binding?.run {
        buttonLoadable.text = ""
        progressBar.isVisible = true
    }

    fun setStateData(text: String) = binding?.run {
        buttonLoadable.setText(text)
        progressBar.isVisible = false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

    override fun setOnClickListener(l: OnClickListener?) {
        binding?.buttonLoadable?.setOnClickListener(l)
    }
}