package zzz.zzzorgo.charter.utils.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.suspendCoroutine

@Singleton
class Network @Inject constructor(context: Context, basicNetwork: BasicNetwork) {
    private val requestQueue: RequestQueue by lazy {
        val cache = DiskBasedCache(context.filesDir, 3 * 1024 * 1024)
        RequestQueue(cache, basicNetwork).apply {
            start()
        }
    }

    private fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    suspend fun <T>makeRequest(url: String, modelClass: Class<T>, options: RequestOptions) =
        suspendCoroutine<T?> { cont ->
            // implement other response data types
            val jsonObjectRequest = XmlRequest(
                options.method,
                url,
                modelClass,
                options.headers,
                options.forceCache,
                Response.Listener { response ->
                    cont.resumeWith(Result.success(response))
                },
                Response.ErrorListener { error ->
                    println(error)
                    cont.resumeWith(Result.failure(error))
                }
            )

            addToRequestQueue(jsonObjectRequest)
        }
}