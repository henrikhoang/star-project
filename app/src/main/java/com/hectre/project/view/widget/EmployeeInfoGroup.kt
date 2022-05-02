package com.hectre.project.view.widget

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyModelGroup
import com.hectre.project.network.model.*
import com.hectre.project.utils.orFalse
import com.hectre.project.view.controller.EmployeesEpoxyController

class EmployeeInfoGroup(
    layoutResId: Int,
    input: TimeSheetInput?,
    jobType: JobType,
    listEmployees: List<Employee>?,
    rateType: RateType?,
    onInteraction: EmployeesEpoxyController.Listener?,
    pieceRate: Double?,
    maxTreesPruning: Boolean?,
    maxTreesThinning: Boolean?
) : EpoxyModelGroup(
    layoutResId,
    buildModels(
        input,
        jobType,
        listEmployees,
        rateType,
        onInteraction,
        pieceRate,
        maxTreesPruning,
        maxTreesThinning
    )
) {

    companion object {
        private fun buildModels(
            input: TimeSheetInput?,
            jobType: JobType,
            listEmployees: List<Employee>?,
            rateType: RateType?,
            onInteraction: EmployeesEpoxyController.Listener?,
            pieceRate: Double?,
            maxTreesPruning: Boolean?,
            maxTreesThinning: Boolean?
        ): List<EpoxyModel<*>> {
            val models = mutableListOf<EpoxyModel<*>>()
            if (listEmployees?.isNotEmpty().orFalse()) {
                models.add(
                    JobTitleItemViewModel_()
                        .id(JobTitleItemView.ID, jobType.value)
                        .jobType(jobType)
                        .onAddMaxTreeClickListener { onInteraction?.onAddMaxTrees(jobType) }
                )
            }

            listEmployees?.forEachIndexed { index, employee ->
                models.add(
                    EmployeeInfoItemViewModel_()
                        .id(EmployeeInfoItemView.ID, index.toString(), employee.name.orEmpty())
                        .name(employee.name.orEmpty())
                        .orchard(input?.orchard)
                        .block(input?.block)
                )
                models.add(RateTypeItemViewModel_()
                    .id(RateTypeItemView.ID, index.toString(), employee.name.orEmpty())
                    .wage(rateType is Wages)
                    .onRateTypeSelectedListener {
                        onInteraction?.onRateTypeSelected(
                            it
                        )
                    }
                )
                when (rateType) {
                    is Wages -> {
                        models.add(
                            InformationItemViewModel_()
                                .id(
                                    InformationItemView.ID,
                                    index.toString(),
                                    employee.name.orEmpty()
                                )
                        )
                    }
                    is PieceRate -> {
                        models.add(
                            RateSettingItemViewModel_()
                                .id(
                                    RateSettingItemView.ID,
                                    index.toString(),
                                    employee.name.orEmpty()
                                )
                                .rate(pieceRate)
                                .applyToAllListener {
                                    onInteraction?.onApplyRateToAll(
                                        it
                                    )
                                }
                        )
                    }
                }

                val rowModels = employee.rows?.mapIndexed { rowIndex, rowData ->
                    RowTickItemViewModel_()
                        .id(rowIndex.toString(), employee.name)
                        .editing(rowData.active.orFalse())
                        .beingWorked(rowData.treesWorked > 0)
                        .rowNumber(rowData.rowNo)
                        .onEditCallback { rowNo, isSelecting ->
                            onInteraction?.onEditRowData(
                                employee,
                                rowData,
                                jobType,
                                isSelecting
                            )
                        }
                }

                rowModels?.let {
                    models.add(RowSelectionCarouselModel_()
                        .id(employee.name.orEmpty(), index.toString())
                        .models(it))
                }

                employee.rows?.filter { it.active.orFalse() }?.forEachIndexed { index, row ->
                    models.add(
                        TreeRowItemViewModel_()
                            .id(TreeRowItemView.ID, index.toString(), employee.name)
                            .rowInfo(row)
                            .maxTrees(when (jobType) {
                                is JobType.Pruning -> maxTreesPruning
                                is JobType.Thinning -> maxTreesThinning
                            })
                    )
                }

                if (index != listEmployees.lastIndex) {
                    models.add(
                        DividerViewModel_()
                            .id(DividerView.ID, index.toString(), employee.name.orEmpty())
                    )
                }
            }
            return models
        }
    }
}