package zzz.zzzorgo.charter.utils.network

import com.android.volley.Cache
import com.android.volley.Request

class RequestOptions {
    var method: Int = Request.Method.GET
    var dataFormat: DataFormat = DataFormat.XML
    var headers: MutableMap<String, String>? = null
    var forceCache: Cache.Entry? = null

    enum class DataFormat {
        XML
    }
}
