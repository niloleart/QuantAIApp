package oleart.nil.rickandmorty.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext
import oleart.nil.rickandmorty.base.CoroutineDispatcherGlobal.Companion.ioDispatcher
import oleart.nil.rickandmorty.base.CoroutineDispatcherGlobal.Companion.uiDispatcher

open class BaseCoroutine : CoroutineScope {

    override val coroutineContext = uiDispatcher + SupervisorJob()

    open fun stopCoroutine() {
        coroutineContext[Job]!!.cancel()
    }

    suspend fun <T> launchAsync(block: suspend CoroutineScope.() -> T) = withContext(ioDispatcher) { block() }
}

class CoroutineDispatcherGlobal {
    companion object {

        var uiDispatcher: CoroutineDispatcher = Dispatchers.Main
        var ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    }
}