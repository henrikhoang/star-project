package com.hectre.project.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.hectre.project.R
import com.hectre.project.utils.bind

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EmployeeInfoItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.employee_info_item_view, this)
    }

    private val tvEmployeeName by bind<AppCompatTextView>(R.id.tvEmployeeName)
    private val ivAvatar by bind<AppCompatTextView>(R.id.ivAvatar)
    private val tvOrchard by bind<AppCompatTextView>(R.id.tvOrchard)
    private val tvBlock by bind<AppCompatTextView>(R.id.tvBlock)

    var name: CharSequence? = null
        @TextProp set
    var orchard: CharSequence? = null
        @TextProp set
    var block: CharSequence? = null
        @TextProp set

    @AfterPropsSet
    fun build() {
        tvEmployeeName.text = name
        tvBlock.text = block
        tvOrchard.text = orchard
        ivAvatar.text = getNameShortCut(name)
    }

    private fun getNameShortCut(char: CharSequence?): CharSequence {
        char ?: return ""
        val eachWordList = char.split(" ")
        return when (eachWordList.size) {
            0 -> "?"
            1 -> if (eachWordList.first().length < 2) {
                eachWordList.first().first().toString().uppercase()
            } else {
                char.substring(0, 2).uppercase()
            }
            else -> "${eachWordList.first().first()}${eachWordList.last().first()}".uppercase()
        }
    }

    companion object {
        const val ID = "EmployeeInfoItemView"
    }

}