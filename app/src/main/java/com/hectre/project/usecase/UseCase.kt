package com.hectre.project.usecase

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class UseCase<in Params, out T> where T : Any {

    private val compositeDisposable = CompositeDisposable()

    abstract fun createObservable(params: Params? = null): T

    protected fun subscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected open fun handleError(throwable: Throwable): Throwable = throwable

    open fun onCleared() {
        compositeDisposable.clear()
    }
}