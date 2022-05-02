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
import com.hectre.project.network.model.JobType
import com.hectre.project.utils.bind

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class JobTitleItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.job_title_item_view, this)
    }

    private val tvJobTitle by bind<AppCompatTextView>(R.id.tvJobTitle)
    private val btnAddMaxTrees by bind<AppCompatTextView>(R.id.tvAddMaxTrees)

    var jobType: JobType? = null
        @ModelProp(ModelProp.Option.DoNotHash) set
    var onAddMaxTreeClickListener: (() -> Unit)? = null
        @CallbackProp set

    @AfterPropsSet
    fun build() {
        when (jobType) {
            is JobType.Pruning -> tvJobTitle.text = context.getString(R.string.lbl_prunning)
            is JobType.Thinning -> tvJobTitle.text =  context.getString(R.string.lbl_thining)
        }
        btnAddMaxTrees.setOnClickListener {
            onAddMaxTreeClickListener?.invoke()
        }
    }

    companion object {
        const val ID = "JobTitleItemView"
    }
}