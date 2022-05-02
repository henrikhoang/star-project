package com.hectre.project.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeSheetInput(
    val orchard: String? = "",
    val block: String? = ""
): Parcelable {
    companion object {
        val SAMPLE = TimeSheetInput("Orchard A", "Sample Block")
    }
}

sealed class JobType(var value: String) {
    class Pruning : JobType("Pruning")
    class Thinning : JobType("Thinning")
}

sealed class RateType
object PieceRate: RateType()
object Wages: RateType()

data class Employees(
    val pruning: List<Employee>? = emptyList(),
    val thinning: List<Employee>? = emptyList()
)

data class Employee(
    val name: String? = null,
    val rows: List<RowData>? = emptyList()
)

data class RowData(
    val rowNo: Int,
    var active: Boolean? = false,
    val total: Int,
    val treesWorked: Int = 0,
    val coWorker: String?
) {
    fun getMaxTrees() = total - treesWorked
}