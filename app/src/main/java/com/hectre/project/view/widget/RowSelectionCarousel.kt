package com.hectre.project.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView
class RowSelectionCarousel @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Carousel(context, attrs, defStyleAttr){
    companion object {
        const val ID = "RowSelectionCarousel"
    }

    override fun getSnapHelperFactory(): SnapHelperFactory? {
        return null
    }

    override fun createLayoutManager(): LayoutManager {
        return LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }
}