package zzz.zzzorgo.charter.data.model.cbr

class CbrCurrencyHistory {
    inner class Root {
        lateinit var DateRange2: String
        lateinit var DateRange1: String
        lateinit var name: String
        lateinit var Record: List<RecordItem>
        lateinit var ID: String
    }

    inner class RecordItem {
        lateinit var Value: String
        lateinit var Id: String
        var Nominal: Int = 1
        lateinit var Date: String
    }

    lateinit var ValCurs: Root
}