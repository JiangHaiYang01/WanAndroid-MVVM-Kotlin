package com.allens.tool

import com.allens.config.Config
import com.allens.model_http.XHttp

object HttpTool {
    var xHttp: XHttp

    init {
        xHttp = XHttp.Builder()
            .baseUrl(Config.baseURL)
            .retryOnConnectionFailure(false)
            .build()
    }
}