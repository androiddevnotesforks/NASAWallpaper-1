package com.mdgroup.nasawallpapers.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

abstract class BaseViewModel : ViewModel() {

    val background = Dispatchers.Default

    private val parentJob by lazy { SupervisorJob() }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        // TODO переписать на кастомный логгер
//        Logger.e(exception.message.toString())
        exception.printStackTrace()
    }

    private val scope: CoroutineScope by lazy { CoroutineScope(parentJob + coroutineExceptionHandler) }

    /**
     * Делает запрос на Dispatchers.Default, для того что б  переключится на другой Dispatcher нужно в блоке вызвать
     * withContext(Dispatchers.Main){}
     */
    fun onBackgroundScope(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = scope.launch(background, start, block)

    open fun showLoading() {}

    open fun hideLoading() {}
}