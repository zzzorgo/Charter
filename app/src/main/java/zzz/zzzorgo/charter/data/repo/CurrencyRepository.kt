package zzz.zzzorgo.charter.data.repo

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import zzz.zzzorgo.charter.data.model.CurrencyHistory
import zzz.zzzorgo.charter.data.model.CurrencyHistoryPoint
import zzz.zzzorgo.charter.data.model.cbr.CbrCurrencyHistory
import zzz.zzzorgo.charter.data.model.cbr.CbrCurrencyList
import zzz.zzzorgo.charter.utils.Network
import zzz.zzzorgo.charter.utils.XmlRequest
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CurrencyRepository @Inject constructor(private val context: Context) {

    suspend fun getCurrencyHistory(currency: Currency, startDate: LocalDateTime): CurrencyHistory? {
        val isoCode = currency.currencyCode
        val mapIsoToCbrCurrency = getCbrCurrencyCodes()
        val cbrCode = mapIsoToCbrCurrency[isoCode]
        val startDateString = startDate.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"))
        val endDateString = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"))

        val url = String.format(
            "https://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s",
            startDateString,
            endDateString,
            cbrCode
        )

        val cbrHistory = makeRequest(url, CbrCurrencyHistory::class.java)

        val history = CurrencyHistory()

        cbrHistory?.ValCurs?.Record?.map {
            history.points.add(CurrencyHistoryPoint(
                LocalDate.parse(it.Date, DateTimeFormatter.ofPattern("d.MM.yyyy")),
                BigDecimal(it.Value.replace(',', '.'))
            ))
        }

        return history
    }

    suspend fun getCbrCurrencyCodes(): Map<String?, String?> {
        val url = "https://www.cbr.ru/scripts/XML_valFull.asp"

        val response = makeRequest(url, CbrCurrencyList::class.java)
        val mapIsoToCbrCurrency = response?.Valuta?.Item?.map {
            it.ISO_Char_Code to it.ParentCode?.trim()
        }?.toMap()

        return mapIsoToCbrCurrency ?: emptyMap()
    }

    suspend fun <T>makeRequest(url: String, modelClass: Class<T>) =
        suspendCoroutine<T?> { cont ->
            val jsonObjectRequest = XmlRequest(
                Request.Method.GET,
                url,
                modelClass,
                null,
                true,
                Response.Listener { response ->
                    cont.resume(response)
                },
                Response.ErrorListener { error ->
                    // TODO: Handle error
                    println(error)
                    cont.resume(null)
                }
            )

            Network.getInstance(context).addToRequestQueue(jsonObjectRequest)
        }
}