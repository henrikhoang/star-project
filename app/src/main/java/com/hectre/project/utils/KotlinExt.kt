package com.hectre.project.utils

import android.view.View
import androidx.annotation.IdRes

sealed class Result<out T> {
    fun getValueOrNull(): T? {
        return if (this is OnSuccess) this.data else null
    }

    data class OnSuccess<out T>(val data: T) : Result<T>()

    data class OnError<out T>(val error: Error) : Result<T>()
}

open class Error(
    val message: String? = null,
    val cause: Throwable? = null
)


fun Boolean?.orFalse() = this ?: false

fun <T: View> View.bind(@IdRes id: Int): Lazy<T>  =
    lazy(LazyThreadSafetyMode.NONE) { findViewById(id)}