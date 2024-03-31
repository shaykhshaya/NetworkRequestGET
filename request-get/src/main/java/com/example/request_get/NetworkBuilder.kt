package com.example.request_get

class NetworkBuilder private constructor(
    private val mBaseUrl: String,
    private val mEndPoint: String,
    private val mQuery: HashMap<String, String>? = null,
    private val mPath: String? = null
) {

    companion object {
        var baseUrl = ""
        var endPoint = ""
        var query: HashMap<String, String>? = null
        var path: String? = null
    }

    init {
        baseUrl = mBaseUrl
        endPoint = mEndPoint
        query = mQuery
        path = mPath
    }

    class Build {

        private var baseUrl: String = ""
        private var endPoint: String = ""
        private var query: HashMap<String, String>? = null
        private var path: String? = null

        /**set BASE_URL*/
        fun baseUrl(url: String) = apply {
            this.baseUrl = url
        }

        /**set END_URL*/
        fun endPoint(url: String) = apply {
            this.endPoint = url
        }

        /**set QUERY*/
        fun query(hashMap: HashMap<String, String>) = apply {
            this.query = hashMap
        }

        /**set PATH*/
        fun path(path: String) = apply {
            this.path = path
        }

        fun create() = NetworkBuilder(
            mBaseUrl = baseUrl,
            mEndPoint = endPoint,
            mQuery = query,
            mPath = path
        )
    }


}