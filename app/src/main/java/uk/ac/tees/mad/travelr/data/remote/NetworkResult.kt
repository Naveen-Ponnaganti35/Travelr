//package uk.ac.tees.mad.travelr.data.remote
//
sealed class NetworkResult<T>(
    val response: T? = null,
    val message: String? = null
) {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(response: T) : NetworkResult<T>(response)
    class Error<T>(message: String?) : NetworkResult<T>(message = message)
}