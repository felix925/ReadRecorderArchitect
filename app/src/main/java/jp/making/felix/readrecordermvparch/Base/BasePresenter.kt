package jp.making.felix.readrecordermvparch.Base

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

interface BasePresenter<T> : CoroutineScope {
    override val coroutineContext: CoroutineContext
    fun takeView(view: T)
    fun start()
}