package com.hectre.project.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.hectre.project.R
import com.hectre.project.network.model.PieceRate
import com.hectre.project.network.model.RateType
import com.hectre.project.network.model.Wages
import com.hectre.project.utils.bind
import com.hectre.project.utils.orFalse

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RateTypeItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.rate_type_item_view, this)
    }

    private val btnPieceRate by bind<AppCompatTextView>(R.id.btnPieceRate)
    private val btnWages by bind<AppCompatTextView>(R.id.btnWages)

    var isWage: Boolean? = false
        @ModelProp set
    var onRateTypeSelectedListener: ((RateType) -> Unit)? = null
        @CallbackProp set

    @AfterPropsSet
    fun build() {
        btnWages.isSelected = isWage.orFalse()
        btnPieceRate.isSelected = !isWage.orFalse()
        btnPieceRate.setOnClickListener {
            onRateTypeSelectedListener?.invoke(PieceRate)
        }
        btnWages.setOnClickListener {
            onRateTypeSelectedListener?.invoke(Wages)
        }
    }

    companion object {
        const val ID = "RateTypeItemView"
    }

}