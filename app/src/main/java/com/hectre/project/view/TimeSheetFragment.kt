package com.hectre.project.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hectre.project.databinding.FragmentTimeSheetBinding
import com.hectre.project.network.model.Employee
import com.hectre.project.network.model.JobType
import com.hectre.project.network.model.RateType
import com.hectre.project.network.model.RowData
import com.hectre.project.view.controller.EmployeesEpoxyController
import com.hectre.project.viewmodel.TimeSheetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeSheetFragment : Fragment(), EmployeesEpoxyController.Listener {

    private var _binding: FragmentTimeSheetBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val epoxyController by lazy {
        EmployeesEpoxyController(this)
    }
    private val viewModel: TimeSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.getEmployees()
        viewModel.employees.observe(viewLifecycleOwner) {
            epoxyController.employees = it
        }
    }

    private fun setupUI() {
        binding.rvEmployees.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAddMaxTrees(jobType: JobType) {
        when (jobType) {
            is JobType.Thinning -> epoxyController.maxTreesThinning = true
            is JobType.Pruning -> epoxyController.maxTreesPruning = true
        }
    }

    override fun onRateTypeSelected(rateType: RateType) {
        epoxyController.rateType = rateType
    }

    override fun onApplyRateToAll(rate: Double) {
        epoxyController.pieceRate = rate
    }

    override fun onEditRowData(
        employee: Employee,
        rowData: RowData,
        jobType: JobType,
        isSelecting: Boolean
    ) {
        val currentEmployees =
            when (jobType) {
                is JobType.Thinning -> epoxyController.employees?.thinning
                is JobType.Pruning -> epoxyController.employees?.pruning
            }
        currentEmployees?.first { it == employee }?.rows?.first { it == rowData }?.active =
            isSelecting
        when (jobType) {
            is JobType.Thinning -> epoxyController.employees = epoxyController.employees?.copy(thinning = currentEmployees)
            is JobType.Pruning -> epoxyController.employees = epoxyController.employees?.copy(pruning = currentEmployees)
    }
}
}