package zzz.zzzorgo.charter.utils.network

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.toolbox.BaseHttpStack
import com.android.volley.toolbox.BasicNetwork
import javax.inject.Inject

class LogBasicNetwork @Inject constructor(httpStack: BaseHttpStack): BasicNetwork(httpStack) {
    override fun performRequest(request: Request<*>?): NetworkResponse {
        println(String.format("%s => %s", request?.url, "pending"))
        val response = super.performRequest(request)
        println(String.format("%s => %d", request?.url, response.statusCode))
        return response;
    }
}