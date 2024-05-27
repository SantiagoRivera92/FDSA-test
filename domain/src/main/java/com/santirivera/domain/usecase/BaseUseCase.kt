package com.santirivera.domain.usecase

import kotlinx.coroutines.*

abstract class BaseUseCase<in Request, Response> {

    private var job = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + job)

    @Throws(Exception::class)
    abstract suspend fun run(params: Request? = null): Response

    private suspend fun invoke(params: Request? = null): Response {
        return withContext(Dispatchers.IO) {
            run(params)
        }
    }

    open fun dispose() {
        job.cancel()
    }

    suspend fun execute(params: Request?) : Response {
        return invoke(params)
    }

}