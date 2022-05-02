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
import com.google.android.material.textfield.TextInputEditText
import com.hectre.project.R
import com.hectre.project.utils.bind

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class RateSettingItemView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.rate_setting_item_view, this)
    }

    private val btnApplyToAll by bind<AppCompatTextView>(R.id.btnApplyToAll)
    private val etRate by bind<TextInputEditText>(R.id.etRate)

    var rate: Double? = null
        @ModelProp set
    var applyToAllListener: ((Double) -> Unit)? = null
        @CallbackProp set

    @AfterPropsSet
    fun build() {
        rate?.let {
            etRate.setText(it.toString())
        }
        btnApplyToAll.setOnClickListener {
            if (etRate.text.isNullOrEmpty()) return@setOnClickListener
            applyToAllListener?.invoke(etRate.text.toString().toDouble())
        }
    }

    companion object {
        const val ID = "RateSettingItemView"
    }
}