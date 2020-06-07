package zzz.zzzorgo.charter.utils.network

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import zzz.zzzorgo.charter.utils.ModelParser
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class XmlRequest<T>(
    method: Int,
    url: String,
    private val modelClass: Class<T>,
    private val headers: MutableMap<String, String>?,
    private val forceCache: Cache.Entry?,
    private val listener: Response.Listener<T>,
    errorListener: Response.ErrorListener
) : Request<T>(method, url, errorListener) {
    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: T) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            var cacheEntry = HttpHeaderParser.parseCacheHeaders(response)
            if (cacheEntry == null && forceCache != null) {
                cacheEntry = forceCache
                cacheEntry.data = response?.data
            }

            val xmlAsString = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

            // implement DI?
            Response.success(
                ModelParser(Gson())
                    .xmlStringToModel(xmlAsString, modelClass), cacheEntry)
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: Exception) {
            Response.error(ParseError(e))
        }
    }
}
