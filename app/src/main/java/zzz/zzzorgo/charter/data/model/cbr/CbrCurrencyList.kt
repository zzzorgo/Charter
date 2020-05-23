package zzz.zzzorgo.charter.data.model.cbr

class CbrCurrencyList {
    inner class CurrencyItem {
        var Name: String? = null
        var EngName: String? = null
        var Nominal: String? = null
        var ParentCode: String? = null
        var ISO_Num_Code: String? = null
        var ISO_Char_Code: String? = null
    }

    inner class Root {
        var Item: List<CurrencyItem>? = null
    }

    var Valuta: Root? = null
}
