package com.hectre.project.usecase

import com.hectre.project.network.model.Employees
import com.hectre.project.repo.TimeSheetRepository
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ViewModelScoped
class GetEmployeesUseCase @Inject constructor(
    private val repo: TimeSheetRepository
) : UseCase<Unit, Single<Employees>>() {

    override fun createObservable(params: Unit?): Single<Employees> {
        return repo.getEmployees()
    }
}