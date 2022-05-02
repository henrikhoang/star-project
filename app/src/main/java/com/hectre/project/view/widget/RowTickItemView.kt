package com.hectre.project.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.hectre.project.R
import com.hectre.project.utils.bind
import com.hectre.project.utils.orFalse

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class RowTickItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.row_tick_item_view, this)
    }

    private val tvRowNo by bind<AppCompatTextView>(R.id.tvRowNo)
    private val ivDot by bind<AppCompatImageView>(R.id.ivDot)

    var isEditing: Boolean? = false
        @ModelProp set
    var isBeingWorked: Boolean? = false
        @ModelProp set
    var rowNumber: Int? = null
        @ModelProp set
    var onEditCallback: ((Int, Boolean) -> Unit)? = null
        @CallbackProp set

    @AfterPropsSet
    fun build() {
        tvRowNo.text = rowNumber.toString()
        ivDot.isVisible = isBeingWorked.orFalse()
        tvRowNo.isSelected = isEditing.orFalse()
        rowNumber?.let { rowNo ->
            setOnClickListener {
                onEditCallback?.invoke(rowNo, !isEditing.orFalse())
            }
        }
    }
}