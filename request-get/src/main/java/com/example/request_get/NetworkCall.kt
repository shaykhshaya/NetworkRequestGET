package com.example.request_get

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NetworkCall<T>(
    private val responseType: Class<T>,
    private val callback: (data: T?, error: String) -> Unit
) {

    suspend fun get() {

        withContext(Dispatchers.IO) {
            var httpURLConnection: HttpURLConnection? = null

            try {
                val fullUrl = getFullUrl(
                    baseUrl = NetworkBuilder.baseUrl,
                    endPoint = NetworkBuilder.endPoint,
                    path = NetworkBuilder.path ?: "",
                    queryParameters = NetworkBuilder.query ?: HashMap()
                )
                val url = URL(fullUrl)
                httpURLConnection = url.openConnection() as HttpURLConnection

                httpURLConnection.apply {
                    requestMethod = "GET"
                    connectTimeout = 10000
                    readTimeout = 10000
                    doInput = true
                }

                val responseCode: Int = httpURLConnection.responseCode
                if (responseCode != 200) {
                    callback(null, responseCode.toString())
                    throw IOException("The error from the server is $responseCode")
                }
                val bufferedReader = BufferedReader(
                    InputStreamReader(httpURLConnection.inputStream)
                )
                val jsonStringHolder = StringBuilder()
                while (true) {
                    val readLine = bufferedReader.readLine() ?: break
                    jsonStringHolder.append(readLine)
                }
                val uerProfileResponse = Gson().fromJson(jsonStringHolder.toString(), responseType)
                callback.invoke(uerProfileResponse, responseCode.toString())


            } catch (ioException: IOException) {
                callback(null, ioException.message.toString())
                Log.e(this.javaClass.name, ioException.message.toString())

            } finally {
                httpURLConnection?.disconnect()
            }
        }
    }

    private fun getFullUrl(
        baseUrl: String,
        endPoint: String,
        path: String,
        queryParameters: HashMap<String, String>
    ): String {
        val urlBuilder = StringBuilder(baseUrl + endPoint + path)
        if (queryParameters.isNotEmpty()) {
            urlBuilder.append("?")
            for ((key, value) in queryParameters) {
                urlBuilder.append("$key=$value&")
            }
            urlBuilder.deleteCharAt(urlBuilder.length - 1)
        }
        return urlBuilder.toString()
    }
}
