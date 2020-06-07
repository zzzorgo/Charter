package zzz.zzzorgo.charter.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun getSeconds(date: LocalDateTime): Long {
    return getMilliseconds(date) / 1000L
}

fun getMilliseconds(date: LocalDateTime): Long {
    return getMilliseconds(date.atZone(ZoneId.systemDefault()))
}

fun getMilliseconds(date: ZonedDateTime): Long {
    return date.toInstant().toEpochMilli()
}

fun getDateStringFromMilliseconds(milliseconds: Long, pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return Instant.ofEpochMilli(milliseconds).atZone(ZoneId.systemDefault()).format(formatter)
}

fun getDateStringFromSeconds(seconds: Long, pattern: String): String {
    return getDateStringFromMilliseconds(seconds * 1000L, pattern)
}

