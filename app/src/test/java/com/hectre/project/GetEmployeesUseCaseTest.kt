package com.hectre.project

import com.hectre.project.network.model.Employees
import com.hectre.project.repo.TimeSheetRepository
import com.hectre.project.usecase.GetEmployeesUseCase
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class GetEmployeesUseCaseTest {

    @Mock
    private lateinit var repo: TimeSheetRepository

    @InjectMocks
    private lateinit var useCase: GetEmployeesUseCase

    @Test
    fun `When gets employees succeeds, valid response should be returned`() {
        val expectedResult = Employees(emptyList(), emptyList())
        given(repo.getEmployees()).willReturn(Single.just(Employees(emptyList(), emptyList())))
        useCase.createObservable().test().assertComplete().assertValue(expectedResult)
        verify(repo).getEmployees()
    }
}