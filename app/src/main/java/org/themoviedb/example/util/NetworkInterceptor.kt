package org.themoviedb.example.util


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            200 -> {

            }

            400 -> {
                // Show Bad Request Error Message
            }

            401 -> {
                // Show UnauthorizedError Message
            }

            402 -> {
                // handleLicenseExpiry(response)
            }

            403 -> {
                // Show Forbidden Message
                //handleLicenseExpiry()
            }

            404 -> {
                // Show NotFound Message
            }
        }
        return response
    }


}
