package zzz.zzzorgo.charter.data

import android.content.Context
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.HurlStack
import zzz.zzzorgo.charter.data.dao.AccountDao
import zzz.zzzorgo.charter.data.dao.CategoryDao
import zzz.zzzorgo.charter.data.dao.RecordDao
import zzz.zzzorgo.charter.data.dao.SettingsDao
import zzz.zzzorgo.charter.data.model.Account
import zzz.zzzorgo.charter.data.model.Category
import zzz.zzzorgo.charter.data.model.Record
import zzz.zzzorgo.charter.data.model.Settings
import zzz.zzzorgo.charter.data.repo.CurrencyRepository
import zzz.zzzorgo.charter.utils.network.Network
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.random.Random

class MockDataCreator() {
    suspend fun populateDatabaseMock(
        context: Context,
        recordDao: RecordDao,
        accountDao: AccountDao,
        settingsDao: SettingsDao,
        categoryDao: CategoryDao
    ) {
        recordDao.deleteAll()
        accountDao.deleteAll()
        settingsDao.deleteAll()
        categoryDao.deleteAll()

        val usdCurrency = Currency.getInstance("USD")
        val rubCurrency = Currency.getInstance("RUB")

        val cashAccount = Account("Наличные").apply {
            id = 1
            currency = rubCurrency
        }
        val depositAccount = Account("Вклад").apply {
            id = 2
            currency = rubCurrency
        }
        val investAccount = Account("Инвестиции").apply {
            id = 3
            currency = usdCurrency
        }

        accountDao.insert(cashAccount)
        accountDao.insert(depositAccount)
        accountDao.insert(investAccount)

        val settings = Settings(rubCurrency)
        settingsDao.insert(settings)

        val nullCategory = Category.nullCategory
        val foodCategory = Category("Еда").apply {
            id = 2
            income = false
            outcome = true
        }

        val transportCategory = Category("Транспорт").apply {
            id = 3
            income = false
            outcome = true
        }

        val homeCategory = Category("Жилье").apply {
            id = 4
            income = false
            outcome = true
        }

        val salaryCategory = Category("Запрлата").apply {
            id = 5
            income = true
            outcome = false
        }

        val royaltyCategory = Category("Проценты").apply {
            id = 6
            income = true
            outcome = false
        }

        categoryDao.insert(nullCategory)
        categoryDao.insert(foodCategory)
        categoryDao.insert(transportCategory)
        categoryDao.insert(homeCategory)
        categoryDao.insert(salaryCategory)
        categoryDao.insert(royaltyCategory)

        val accounts = listOf(cashAccount, depositAccount, investAccount)
        val outcomeCategories = listOf(nullCategory, foodCategory, transportCategory, homeCategory)
        val incomeCategories = listOf(nullCategory, salaryCategory, royaltyCategory)

        val outcomeRecords = (1..2000).map {
            val category = outcomeCategories.random()
            val record = Record(category.id).apply {
                val account = accounts.random()
                valueFrom = BigDecimal(Random.nextLong(100, 400000)).divide(BigDecimal(100L))
                accountFrom = account.id
                currencyFrom = account.currency
                date = getRandomDate()
            }

            record
        }

        val incomeRecords = (1..600).map {
            val category = incomeCategories.random()
            val record = Record(category.id).apply {
                val account = accounts.random()
                valueTo = BigDecimal(Random.nextLong(100000, 4000000)).divide(BigDecimal(100L))
                accountTo = account.id
                currencyTo = account.currency
                date = getRandomDate()
            }

            record
        }

        val currencyRepository = CurrencyRepository(Network(context, BasicNetwork(HurlStack())))
        val currencyValues = currencyRepository.getCbrCurrencyForToday()

        val transferRecords = (1..600).map {
            val category = nullCategory
            val record = Record(category.id).apply {
                val accountTo = accounts.random()
                val accountFrom = accounts.random()

                val _valueFrom = BigDecimal(Random.nextLong(100, 400000))
                    .divide(BigDecimal(100L))

                valueFrom = _valueFrom
                valueTo = _valueFrom * BigDecimal(currencyValues[accountFrom.currency]) / BigDecimal(currencyValues[accountTo.currency])

                currencyTo = accountTo.currency
                currencyFrom = accountFrom.currency

                date = getRandomDate()
            }

            record
        }

        recordDao.insertAll(*outcomeRecords.toTypedArray())
        recordDao.insertAll(*incomeRecords.toTypedArray())
        recordDao.insertAll(*transferRecords.toTypedArray())
    }

    fun getRandomDate(): LocalDateTime {
        val max = Date().time
        val min = 1527361064000L

        val random = Random.nextLong(min, max)

        return Instant.ofEpochMilli(random).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}
//
//class Lol() { var resources: List<CbrCurrencyHistory>? = null }
//
//fun populateCurrencyHistory(context: Context) {
//    val xmlInputStream: InputStream = context.resources.openRawResource(R.raw.cbr_currency_histories)
//    val xmlString = BufferedReader(InputStreamReader(xmlInputStream))
//        .lines().collect(Collectors.joining("\n"))
//    val converter = Converter(Gson())
//    val data = converter.xmlStringToModel(xmlString, Lol::class.java)
//    println(data)
//}