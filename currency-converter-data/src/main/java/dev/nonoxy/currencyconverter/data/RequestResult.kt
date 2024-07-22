package dev.nonoxy.restaurant_reviews.data

sealed class RequestResult<E: Any>(open val data: E? = null) {

    class InProgress<E: Any> : RequestResult<E>()
    class Success<E: Any>(override val data: E) : RequestResult<E>(data)
    class Error<E: Any>(val error: Throwable? = null) : RequestResult<E>()
}

fun <I: Any, O: Any> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(mapper(data))
        is RequestResult.InProgress -> RequestResult.InProgress()
        is RequestResult.Error -> RequestResult.Error(error)
    }
}

internal fun <T: Any> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(getOrThrow())
        isFailure -> RequestResult.Error(exceptionOrNull())
        else -> error("Impossible branch")
    }
}