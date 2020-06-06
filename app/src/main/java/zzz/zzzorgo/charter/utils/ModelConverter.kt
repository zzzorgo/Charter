package zzz.zzzorgo.charter.utils

import com.google.gson.Gson
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import javax.inject.Inject

class ModelConverter @Inject constructor(val gson: Gson){
    fun <T>xmlStringToModel(xml: String, modelClass: Class<T>): T {
        val xmlToJson = XmlToJson.Builder(xml).build()
        val jsonString = xmlToJson.toString()
        return jsonStringToModel(jsonString, modelClass)
    }

    fun <T>jsonStringToModel(json: String, modelClass: Class<T>): T {
        return gson.fromJson(json, modelClass)
    }
}