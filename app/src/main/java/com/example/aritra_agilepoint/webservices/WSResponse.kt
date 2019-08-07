package com.example.aritra_agilepoint.webservices

import androidx.annotation.IntDef
import java.io.Serializable
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

class WSResponse: Serializable {

    companion object {
        const val SUCCESS = 1
        const val FAILURE = 0
    }

    @IntDef(SUCCESS, FAILURE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class WSResponsePref;

    private var responseCode: Int = 0
    private var responseMessage: String? = null

    fun getResponseCode(): Int {
        return responseCode
    }

    fun getResponseMessage(): String? {
        return responseMessage
    }

    fun setResponseCode(responseCode: Int) {
        this.responseCode = responseCode
    }

    fun setResponseMessage(responseMessage: String) {
        this.responseMessage = responseMessage
    }
}