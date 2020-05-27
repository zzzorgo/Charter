package zzz.zzzorgo.charter.utils

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.*
import com.google.gson.Gson
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit


class LogBasicNetwork(httpStack: BaseHttpStack): BasicNetwork(httpStack) {
    override fun performRequest(request: Request<*>?): NetworkResponse {
        println(String.format("%s => %s", request?.url, "pending"))
        val response = super.performRequest(request)
        println(String.format("%s => %d", request?.url, response.statusCode))
        return response;
    }
}

class Network constructor(context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: Network? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: Network(context).also {
                    INSTANCE = it
                }
            }
    }

    val requestQueue: RequestQueue by lazy {
        // applicationContext is key, it keeps you from leaking the
        // Activity or BroadcastReceiver if someone passes one in.
        val cache = DiskBasedCache(context.filesDir, 1024 * 1024)
        val network = LogBasicNetwork(HurlStack())
        RequestQueue(cache, network).apply {
            start()
        }
    }
    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}


class XmlRequest<T>(
    method: Int,
    url: String,
    private val modelClass: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val forceCache: Boolean = false,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(method, url, errorListener) {
    private val gson = Gson()
    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            var cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
            if (cacheEntry == null && forceCache) {
                cacheEntry = getForcedCachedEntry(response?.data)
            }

            val xmlAsString = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            val xmlToJson = XmlToJson.Builder(xmlAsString).build()
            val jsonString = xmlToJson.toString()

            Response.success(gson.fromJson(jsonString, modelClass), cacheEntry)
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }
}

fun getForcedCachedEntry(data: ByteArray? = ByteArray(0)): Cache.Entry {
    val cacheEntry = Cache.Entry()
    val expires = ZonedDateTime
        .now()
        .plusDays(1)
        .truncatedTo(ChronoUnit.DAYS)
        .toInstant()
        .toEpochMilli()
    cacheEntry.data = data
    cacheEntry.softTtl = expires
    cacheEntry.ttl = expires

    return cacheEntry
}