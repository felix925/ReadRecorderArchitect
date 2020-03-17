package jp.making.felix.readrecordermvparch.Base

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

interface BasePresenter<T>: CoroutineScope{
    override val coroutineContext: CoroutineContext

    fun start()

    @CallSuper
    fun dropView(){
        coroutineContext.cancel()
    }
}