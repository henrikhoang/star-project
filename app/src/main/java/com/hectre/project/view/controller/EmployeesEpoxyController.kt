package com.hectre.project.view.controller

import com.airbnb.epoxy.EpoxyController
import com.hectre.project.R
import com.hectre.project.network.model.*
import com.hectre.project.view.widget.EmployeeInfoGroup
import com.hectre.project.view.widget.SpaceView
import com.hectre.project.view.widget.spaceView

class EmployeesEpoxyController(
    private val onInteraction: Listener?,
) : EpoxyController() {

    var employees: Employees? = null
        set(value) {
            field = value
            requestModelBuild()
        }
    var input: TimeSheetInput? = TimeSheetInput.SAMPLE
        set(value) {
            field = value
            requestModelBuild()
        }
    var rateType: RateType? = PieceRate
        set(value) {
            field = value
            requestModelBuild()
        }
    var pieceRate: Double? = null
        set(value) {
            field = value
            requestModelBuild()
        }
    var maxTreesPruning: Boolean? = null
        set(value) {
            field = value
            requestModelBuild()
        }
    var maxTreesThinning: Boolean? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        buildList(employees?.pruning, JobType.Pruning())

        spaceView {
            id(SpaceView.ID)
        }

        buildList(employees?.thinning, JobType.Thinning())
    }


    private fun buildList(listEmployees: List<Employee>?, jobType: JobType) {
        EmployeeInfoGroup(
            R.layout.employee_info_group, input,
            jobType,
            listEmployees,
            rateType,
            onInteraction,
            pieceRate,
            maxTreesPruning,
            maxTreesThinning
        ).addTo(this)
    }

    interface Listener {
        fun onAddMaxTrees(jobType: JobType)
        fun onRateTypeSelected(rateType: RateType)
        fun onApplyRateToAll(rate: Double)
        fun onEditRowData(
            employee: Employee,
            rowData: RowData,
            jobType: JobType,
            isSelecting: Boolean
        )
    }
}