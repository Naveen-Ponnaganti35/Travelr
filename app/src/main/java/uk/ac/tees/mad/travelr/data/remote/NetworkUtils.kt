package uk.ac.tees.mad.travelr.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

//class NetworkUtils {
//    private fun <T> performNetworkRequest(networkRequest: suspend () -> Response<T>): Flow<NetworkResult<T>> =
//        flow<NetworkResult<T>> {
//            try {
//                val response = networkRequest.invoke()
//
//                // error from api
//                if (!response.isSuccessful) {
//                    response.errorBody()?.string()?.let { error ->
//                        emit(
//                            NetworkResult.Error(
//                                "Unknown error: with code: ${response.code()} , message :${response.message()}"
//                            )
//                        )
//                    } ?: run {
//                        emit(
//                            NetworkResult.Error(
//                                "Unknown error: with code: ${response.code()} , message :${response.message()}"
//                            )
//                        )
//                    }
//                } else {
//                    val newsResponse = response.body()
//                    if (newsResponse != null) {
//                        emit(NetworkResult.Success(newsResponse))
//                    } else {
//                        emit(
//                            NetworkResult.Error(
//                                "Response body is null"
//                            )
//                        )
//                    }
//                }
//            } catch (e: Exception) {
//                throw e
//            }
//
//        }.catch { e ->
//            Log.d(TAG, "performNetworkRequest: " + e.message)
//            emit(
//                NetworkResult.Error(
//                    "Unable to fetch data with exception: ${e.message}"
//                )
//            )
//
//        }.flowOn(Dispatchers.IO)
//
//
//    companion object {
//        const val TAG = "NetworkUtils"
//    }
//}