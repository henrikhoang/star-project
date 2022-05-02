package com.hectre.project.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hectre.project.network.model.Employees
import com.hectre.project.usecase.GetEmployeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TimeSheetViewModel @Inject constructor(
    private val useCase: GetEmployeesUseCase
): ViewModel() {

    private val _employees = MutableLiveData<Employees>()
    val employees: LiveData<Employees> get() = _employees

    fun getEmployees() {
        useCase.createObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _employees.value = it
            }, {})
    }
}