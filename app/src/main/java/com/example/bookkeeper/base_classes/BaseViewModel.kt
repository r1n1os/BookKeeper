package com.example.bookkeeper.base_classes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope{

    private val job = Job()
    var errorResponse = MutableLiveData<String>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun onError(errorCode: Int, errorMessage: String){

    }
}