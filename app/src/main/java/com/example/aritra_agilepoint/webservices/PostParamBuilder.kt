package com.example.aritra_agilepoint.webservices

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.LinkedHashMap

class PostParamBuilder {
    fun prepareParam(hashParam: LinkedHashMap<String, Any>?): String {
        val strBuilder = StringBuilder()

        if (hashParam != null && hashParam.size > 0) {
            val keyset = hashParam.keys
            strBuilder.append("/")
            for (key in keyset) {
                strBuilder.append(hashParam[key])
                break
            }
        }
        return strBuilder.toString()
    }

    fun prepareQuery(querys: LinkedHashMap<String, Any>?): String {
        val strBuilder = StringBuilder()
        try {
            if (querys != null && querys.size > 0) {
                //                strBuilder.append("?");
                var i = 0
                for ((key, value) in querys) {
                    if (value != null) {
                        strBuilder.append("$key=$value")
                        i++
                        if (i < querys.size) {
                            strBuilder.append("&")
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            return strBuilder.toString()
        }
    }
}