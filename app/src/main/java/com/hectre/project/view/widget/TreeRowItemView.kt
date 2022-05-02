package com.hectre.project.view.widget

import android.content.Context
import android.text.InputFilter
import android.text.Spanned
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.material.textfield.TextInputEditText
import com.hectre.project.R
import com.hectre.project.network.model.RowData
import com.hectre.project.utils.bind
import com.hectre.project.utils.orFalse

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class TreeRowItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.tree_row_item_view, this)
    }

    private val tvTreeRow by bind<AppCompatTextView>(R.id.tvTreeRow)
    private val etInput by bind<TextInputEditText>(R.id.etInput)
    private val tvTotalTrees by bind<AppCompatTextView>(R.id.tvTotalTrees)
    private val tvCoWorker by bind<AppCompatTextView>(R.id.tvCoWorker)

    var isMaxTrees: Boolean? = null
        @ModelProp set
    var rowInfo: RowData? = null
        @ModelProp set

    @AfterPropsSet
    fun build() {
        rowInfo?.let{
            etInput.filters = arrayOf(MinMaxFilter(0, it.getMaxTrees()))
            tvTreeRow.text = context.getString(R.string.tree_row_number, it.rowNo)
            tvTotalTrees.text = context.getString(R.string.total_trees, it.total)
            if (isMaxTrees.orFalse()) {
                etInput.setText((rowInfo?.getMaxTrees() ?: 0).toString())
            }
            tvCoWorker.text = context.getString(R.string.cowoker_worked, it.coWorker, it.treesWorked)
        }
    }

    companion object {
        const val ID = "TreeRowItemView"
    }

    inner class MinMaxFilter(
        private val minValue: Int = 0,
        private val maxValue: Int = 0
    ) : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dStart: Int,
            dEnd: Int
        ): CharSequence? {
            try {
                val input = Integer.parseInt(dest.toString() + source.toString())
                if (isInRange(minValue, maxValue, input))
                    return source
            } catch (nfe: NumberFormatException) {
            }
            return ""
        }

        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }
}