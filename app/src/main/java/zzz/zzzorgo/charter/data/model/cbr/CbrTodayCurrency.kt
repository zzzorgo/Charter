package zzz.zzzorgo.charter.data.model.cbr

class CbrTodayCurrency {
    class TodayCurrencyItem {
        lateinit var ID: String
        lateinit var NumCode: String
        lateinit var CharCode: String
        var Nominal: Int = 1
        lateinit var Name: String
        lateinit var Value: String
    }

    class Root {
        lateinit var Valute: List<TodayCurrencyItem>
        lateinit var name: String
        lateinit var Date: String

    }
    lateinit var ValCurs: Root
}
