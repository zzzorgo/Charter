package zzz.zzzorgo.charter.data.model

import java.math.BigDecimal
import java.time.LocalDate

class CurrencyHistoryPoint(val date: LocalDate, val value: BigDecimal) {}

class CurrencyHistory {
    val points: MutableList<CurrencyHistoryPoint> = mutableListOf()
}