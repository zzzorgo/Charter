package zzz.zzzorgo.charter.data.repo

import androidx.lifecycle.MutableLiveData
import com.android.volley.Cache
import zzz.zzzorgo.charter.data.model.cbr.CbrTodayCurrency
import zzz.zzzorgo.charter.utils.network.Network
import zzz.zzzorgo.charter.utils.network.RequestOptions
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(private val network: Network) {

    val mapCurrencyToValue = MutableLiveData(mutableMapOf<Currency, String>())

    suspend fun getCbrCurrencyForToday(): MutableMap<Currency, String> {
        val url = "https://www.cbr.ru/scripts/XML_daily.asp"

        val options = RequestOptions().apply {
            forceCache = expiresTomorrowCache()
        }

        val response = network.makeRequest(url, CbrTodayCurrency::class.java, options)

        val _mapCurrencyToValue = response?.ValCurs?.Valute?.map {
            Currency.getInstance(it.CharCode) to it.Value.replace(',', '.')
        }?.toMap()?.toMutableMap()

        _mapCurrencyToValue?.set(Currency.getInstance("RUB"), "1")

        mapCurrencyToValue.postValue(_mapCurrencyToValue)
        return _mapCurrencyToValue ?: mutableMapOf()
    }

    private fun expiresTomorrowCache(): Cache.Entry {
        val cacheEntry = Cache.Entry()
        val expires = ZonedDateTime
            .now()
            .plusDays(1)
            .truncatedTo(ChronoUnit.DAYS)
            .toInstant()
            .toEpochMilli()
        cacheEntry.softTtl = expires
        cacheEntry.ttl = expires

        return cacheEntry
    }
}