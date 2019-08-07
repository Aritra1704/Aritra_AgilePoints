package com.example.aritra_agilepoint.webservices

import com.example.aritra_agilepoint.BuildConfig

class WSConstants {

    companion object {
        val TYPE_DEFAULT = 0

        val STATUS_SUCCESS = 200
        val STATUS_CREATED = 201
        val STATUS_ACCEPTED = 202
        val STATUS_NO_CONTENT = 204
        val STATUS_FAILED = 500
        val STATUS_INVALID_SERVICE = 501
        val STATUS_INVALID_API_KEY = 401

        val READ_TIMEOUT: Long = 60 * 1000//10 * 1000;//60000;
        val WRITE_TIMEOUT: Long = 60 * 1000//10 * 1000;//60000;
        val CONNECT_TIMEOUT: Long = 10 * 1000//10000;
        val IMAGE_WRITE_TIMEOUT = 120000

        val GET = "GET"
        val POST = "POST"

        val RESPONSE_STATUS = "ok"
        val RESPONSE_BODY = "response"
        val RESPONSE_ERROR = "error"
        val RESPONSE_STATUS_TRUE = "true"
        val RESPONSE_REASON = "reason"

        val DOWNLOADPATH = "DOWNLOADPATH"
        val FILENAME = "FILENAME"

        val STAT_SUCCESS = "SUCCESS"
        val STAT_FAILURE = "FAILURE"
    }


    object ServerKeys {
        val contentType = "Content-Type"
        val apiKey = "x-zippr-api-key"
        val sessionToken = "x-zippr-sessiontoken"
        val CONTENT_TYPE = "application/json"

        val URL = "url"
        val CONTENT = "content"
        val CONTENT_VAL = "content_val"
        val API_KEY = "api_key"
        val API_KEY_VAL = "api_key_val"
        val SESSION_TOKEN = "session_token"
        val SESSION_TOKEN_VAL = "session_token_val"

        val PARAM = "param"
        val QUERY = "query"
        val BODY = "body"
        val MORE = "more"
        val MULTIPART = "multipart"
    }

    object APIInputs {
        val param = "param"
        val body = "body"
    }

    object Error {
        val CODE = "code"
        val REASON = "reason"
    }

}