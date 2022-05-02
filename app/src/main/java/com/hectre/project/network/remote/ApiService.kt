package com.hectre.project.network.remote

import com.hectre.project.network.model.Employees
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    fun getEmployees(): Single<Employees>
}