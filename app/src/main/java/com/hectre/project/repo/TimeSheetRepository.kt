package com.hectre.project.repo

import com.hectre.project.network.model.Employees
import com.hectre.project.network.remote.ApiService
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@ViewModelScoped
class TimeSheetRepository @Inject constructor(
    private val service: ApiService
) {

    fun getEmployees(): Single<Employees> {
        return service.getEmployees()
    }
}