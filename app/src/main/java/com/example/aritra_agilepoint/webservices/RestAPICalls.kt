package com.example.aritra_agilepoint.webservices

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.arpaul.utilitieslib.FileUtils
import com.arpaul.utilitieslib.JSONUtils
import com.example.aritra_agilepoint.BuildConfig
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.CONNECT_TIMEOUT
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.FILENAME
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.READ_TIMEOUT
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.RESPONSE_BODY
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.RESPONSE_ERROR
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.RESPONSE_STATUS
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.RESPONSE_STATUS_TRUE
import com.example.aritra_agilepoint.webservices.WSConstants.Companion.WRITE_TIMEOUT
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.BODY
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.CONTENT
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.CONTENT_TYPE
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.CONTENT_VAL
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.MORE
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.MULTIPART
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.PARAM
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.QUERY
import com.example.aritra_agilepoint.webservices.WSConstants.ServerKeys.URL
import com.example.aritra_agilepoint.webservices.WSType.WSTypePref.Companion.DELETE
import com.example.aritra_agilepoint.webservices.WSType.WSTypePref.Companion.DOWNLOAD_FILE
import com.example.aritra_agilepoint.webservices.WSType.WSTypePref.Companion.GET
import com.example.aritra_agilepoint.webservices.WSType.WSTypePref.Companion.POST
import com.example.aritra_agilepoint.webservices.WSType.WSTypePref.Companion.PUT
import com.example.aritra_agilepoint.webservices.WSType.WSTypePref.Companion.UPLOAD_FILE
import okhttp3.*
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.ArrayList
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.concurrent.TimeUnit

class RestAPICalls {
    private val TAG = "RestAPICalls"
    private var url = ""
    private var sessionToken = ""
    val api_key: String? = null
    private var header: LinkedHashMap<String, Any>? = null
    private var baseURl = BuildConfig.BASE_URL;
    private var params: LinkedHashMap<String, Any>? = null
    private var query: LinkedHashMap<String, Any>? = null
    private var body: LinkedHashMap<String, Any>? = null
    private var more: LinkedHashMap<String, Any>? = null
    private val DEBUG = BuildConfig.DEBUG
    private var type: Int = 0
    private var responseDo: WSResponse? = null

    private var okHttpClient: OkHttpClient? = null
    private var httpBuilder: HttpUrl.Builder? = null
    private var param: String? = ""
    private var queryPar = ""
    private val mHeadersMap = HashMap<String, String>()
    private val REQUEST_TAG = "okhttprequest"
    private val attachmentFileName = "bitmap.png"
    private var isMultipart = true
    private val context: Context? = null

    /**
     * Rest API calls
     * @param url
     * @param params
     * @param body
     * @param type
     */
    constructor(
        url: String,
        sessionToken: String,
        params: LinkedHashMap<String, Any>,
        query: LinkedHashMap<String, Any>,
        body: LinkedHashMap<String, Any>, @WSType.WSTypePref type: Int
    ) {
        this.url = url
        this.sessionToken = sessionToken
        this.params = params
        this.query = query
        this.body = body
        this.type = type

        init()
    }

    /**
     * Rest API Calls for Image.
     * Make sure Image api is initiated in gradle file.
     * @param url
     * @param sessionToken
     * @param params
     * @param query
     * @param body
     * @param type
     * @param isImage
     */
    constructor(
        url: String,
        sessionToken: String,
        params: LinkedHashMap<String, Any>,
        query: LinkedHashMap<String, Any>,
        body: LinkedHashMap<String, Any>, @WSType.WSTypePref type: Int,
        isImage: Boolean
    ) {
//        if (isImage)
//            this.baseURl = BuildConfig.IMAGE_URL;
        this.url = url
        this.sessionToken = sessionToken
        this.params = params
        this.query = query
        this.body = body
        this.type = type

        init()
    }

    /**
     * Rest API calls for custom baseUrl.
     * Send all the headers via hashmap
     * @param baseURl
     * @param header
     * @param extras
     * @param type
     */
    constructor (
        baseURl: String,
        header: LinkedHashMap<String, Any>,
        extras: LinkedHashMap<String, Any>, @WSType.WSTypePref type: Int
    ) {
        this.baseURl = baseURl
        this.header = header
        if (extras.containsKey(PARAM))
            this.params = extras[PARAM] as LinkedHashMap<String, Any>?
        if (extras.containsKey(QUERY))
            this.query = extras[QUERY] as LinkedHashMap<String, Any>?
        if (extras.containsKey(BODY))
            this.body = extras[BODY] as LinkedHashMap<String, Any>?
        if (extras.containsKey(MORE))
            this.more = extras[MORE] as LinkedHashMap<String, Any>?
        if (extras.containsKey(MULTIPART))
            this.isMultipart = extras[MULTIPART] as Boolean
        this.type = type

        init()
    }

    fun getData(): WSResponse? {
        return responseDo
    }

    private fun init() {
        this.responseDo = WSResponse()

        val builder = OkHttpClient.Builder()

        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)

        val cookieJar = object : CookieJar {
            private val cookieStore = HashMap<HttpUrl, List<Cookie>>()
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url] = cookies
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url]
                return cookies ?: ArrayList()
            }
        }
        builder.cookieJar(cookieJar)

        okHttpClient = builder.build()

        setupHeaders()
        // Session token is set by the caller.

        httpBuilder = HttpUrl.parse(baseURl + url)!!.newBuilder()

        if (params != null && !params!!.isEmpty())
            param = PostParamBuilder().prepareParam(params)

        if (query != null && !query!!.isEmpty()) {
            queryPar = PostParamBuilder().prepareQuery(query)
            httpBuilder!!.query(queryPar)
        }

        callAPI()
    }

    private fun setupHeaders() {
        header?.let {
            if (it.size > 0) {
                if (it.containsKey(URL))
                    url = it[URL] as String

                if (it.containsKey(CONTENT)) {
                    mHeadersMap[it[CONTENT] as String] = it[CONTENT_VAL] as String
                }
            }
        }?: run {
            mHeadersMap[WSConstants.ServerKeys.contentType] = CONTENT_TYPE
            if (!TextUtils.isEmpty(sessionToken))
                mHeadersMap[WSConstants.ServerKeys.sessionToken] = sessionToken
        }
    }

    private fun callAPI() {
        val request: Request?
        var builder = Request.Builder()

        var downloadPath = ""
        var fileName = ""
        var status = -1
        try {
            when (type) {
                GET -> builder = getMethodCall()

                PUT -> builder = putMethodCall()

                POST -> builder = postMethodCall()

                DELETE -> builder = deleteMethodCall()

                DOWNLOAD_FILE -> {
                    builder = downloadFileCall()

//                    downloadPath = params!![DOWNLOADPATH] as String?
//                    fileName = params!![FILENAME] as String?
                }

                UPLOAD_FILE -> builder = uploadFileCall()
            }
            request = builder.build()
            val call = okHttpClient!!.newCall(request!!)

            if (DEBUG && request != null) {
                var strType = ""
                when (type) {
                    GET -> strType = "GET"
                    POST -> strType = "POST"
                    PUT -> strType = "PUT"
                    DELETE -> strType = "DELETE"
                    DOWNLOAD_FILE -> strType = "DOWNLOAD_FILE"
                    UPLOAD_FILE -> strType = "UPLOAD_FILE"
                }
                val requestDump = String.format(
                    "****Request: %s API: %s\nURL: %s\nHeaders:%s\nParam:%s\nQuery:%s\nBody:%s",
                    strType, //requestType
                    url, //URL
                    request.url(), //Complete URL
                    request.headers(), //Request Header
                    if (param == null) "empty" else param!!.toString(), //Request params
                    if (query == null) "empty" else query!!.toString(), //Request params
                    if (request.body() == null) "empty" else bodyToString(request)//Request body
                )
                Log.d(TAG, requestDump)
            }

            val response = call.execute()
            if (response != null) {

                status = response.code()
                when (status) {
                    WSConstants.STATUS_SUCCESS, WSConstants.STATUS_CREATED, WSConstants.STATUS_ACCEPTED, WSConstants.STATUS_NO_CONTENT -> {
                        responseDo!!.setResponseCode(WSResponse.SUCCESS)

                        if (type == DOWNLOAD_FILE)
                            FileUtils.saveInputStreamAsFile(response.body()!!.byteStream(), downloadPath, fileName)
                        else {
                            val resp = response.body()!!.string()
                            if (DEBUG) Log.i(TAG, resp)
                            val joResponse: JSONObject
                            val responseStatus: String
                            val responseBody: String
                            if (!TextUtils.isEmpty(resp)) {
                                joResponse = JSONObject(resp)
                                responseStatus = joResponse.getString(RESPONSE_STATUS)
                                responseBody = joResponse.getString(RESPONSE_BODY)
                            } else {
                                responseStatus = RESPONSE_STATUS_TRUE
                                responseBody = resp
                            }
                            if (responseStatus.equals(RESPONSE_STATUS_TRUE, ignoreCase = true))
                                responseDo!!.setResponseMessage(responseBody)
                            else {
                                responseDo!!.setResponseCode(WSResponse.FAILURE)
                                responseDo!!.setResponseMessage(responseBody)
                            }
                        }
                    }

                    WSConstants.STATUS_FAILED -> {
                        responseDo!!.setResponseCode(WSResponse.FAILURE)
                        val errResp = response.body()!!.string()
                        if (!TextUtils.isEmpty(errResp)) {
                            if (DEBUG) Log.i(TAG, errResp.toString())
                            val joError = JSONObject(errResp)
                            if (JSONUtils.hasJSONtag(joError, RESPONSE_ERROR)) {
                                val responseError = joError.getString(RESPONSE_ERROR)
                                responseDo!!.setResponseMessage(responseError)
                            }
                        } else
                            responseDo!!.setResponseMessage(response.body()!!.string())
                    }
                    else -> {
                        responseDo!!.setResponseCode(WSResponse.FAILURE)
                        val errResp = response.body()!!.string()
                        if (!TextUtils.isEmpty(errResp)) {
                            if (DEBUG) Log.i(TAG, errResp.toString())
                            val joError = JSONObject(errResp)
                            if (JSONUtils.hasJSONtag(joError, RESPONSE_ERROR)) {
                                val responseError = joError.getString(RESPONSE_ERROR)
                                responseDo!!.setResponseMessage(responseError)
                            }
                        } else
                            responseDo!!.setResponseMessage(response.body()!!.string())
                    }
                }
                if (DEBUG) {
                    val responseDump = String.format(
                        "****Response: %s %s\n%s\n%s %s\n%s",
                        request.method(),
                        url,
                        request.headers(),
                        "" + response.code(),
                        if (response == null) "empty" else response.message(),
                        if (responseDo == null) "empty" else responseDo!!.getResponseMessage()
                    )
                    Log.i(TAG, responseDump)
                }
            }
        } catch (ex: JSONException) {
            ex.printStackTrace()
            responseDo!!.setResponseCode(WSResponse.FAILURE)
            responseDo!!.setResponseMessage(ex.message!!)
        } catch (ex: SocketTimeoutException) {
            ex.printStackTrace()
            responseDo!!.setResponseCode(WSResponse.FAILURE)
            responseDo!!.setResponseMessage(ex.message!!)
        } catch (ex: IOException) {
            ex.printStackTrace()
            responseDo!!.setResponseCode(WSResponse.FAILURE)
            responseDo!!.setResponseMessage(ex.message!!)
        } catch (ex: Exception) {
            ex.printStackTrace()
            responseDo!!.setResponseCode(WSResponse.FAILURE)
            responseDo!!.setResponseMessage(ex.message!!)
        } finally {

        }
    }

    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            val responseString = String(response!!.body()!!.bytes())

            Log.d("OkHttp-NET-Interceptor", "Response: $responseString")

            //            String content = UtilityMethods.convertResponseToString(response);
            //            LOG.debugLog(TAG, lastCalledMethodName + " - " + content);
            return response.newBuilder().body(
                ResponseBody.create(
                    response.body()!!.contentType(),
                    if (response != null) response.body()!!.string() else ""
                )
            ).build()
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            if (responseDo != null) {
                responseDo!!.setResponseCode(WSResponse.FAILURE)
                responseDo!!.setResponseMessage(exception.message!!)
            }
        }

        return chain.proceed(chain.request())
    }

    /**
     * GET method call
     * @return
     */
    private fun getMethodCall(): Request.Builder {
        val builder = Request.Builder()

        builder.url(httpBuilder!!.build().toString() + param!!)
        builder.headers(Headers.of(mHeadersMap))
        builder.tag(REQUEST_TAG)

        return builder
    }

    /**
     * PUT method call
     * @return
     */
    private fun putMethodCall(): Request.Builder {
        val builder = Request.Builder()

        var requestBody: RequestBody? = null
        if (body != null && body!!.size > 0) {
            val objBody = JSONObject(body.toString())
            requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE), objBody.toString())
        }

        builder.url(httpBuilder!!.build().toString() + param!!)
        builder.headers(Headers.of(mHeadersMap))
        if (requestBody != null)
            builder.put(requestBody)
        builder.tag(REQUEST_TAG)

        return builder
    }

    /**
     * POST method call
     * @return
     */
    private fun postMethodCall(): Request.Builder {
        val builder = Request.Builder()

        //        MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
        //
        //        multiBuilder.setType(MultipartBody.FORM);
        //        if(body != null && body.size() > 0) {
        //            Set<String> keyset = body.keySet();
        //            for (String key : keyset) {
        //                multiBuilder.addFormDataPart(key, (String) body.get(key));
        //            }
        //        }
        //
        //        RequestBody requestBody = multiBuilder.build();

        var requestBody: RequestBody? = null
        if (body != null && body!!.size > 0) {
            val joBody = JSONObject(body.toString())
            requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE), joBody.toString())
        }

        builder.url(httpBuilder!!.build().toString() + param!!)
        builder.headers(Headers.of(mHeadersMap))

        builder.method("POST", RequestBody.create(null, ByteArray(0)))
        if (requestBody != null) {
            builder.post(requestBody)
            //            builder.method("POST", RequestBody.create(null, new byte[0])).post(requestBody);
        }

        builder.tag(REQUEST_TAG)

        return builder
    }

    /**
     * DELETE method call
     * @return
     */
    private fun deleteMethodCall(): Request.Builder {
        val builder = Request.Builder()


        builder.url(httpBuilder!!.build().toString() + param!!)
        builder.headers(Headers.of(mHeadersMap))

        if (body != null) {
            val po = JSONObject(body.toString())
            val requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE), po.toString())
            builder.method("DELETE", RequestBody.create(null, ByteArray(0))).delete(requestBody)
        } else
            builder.delete()

        builder.tag(REQUEST_TAG)

        return builder
    }

    /**
     * DOWNLOAD file call
     * @return
     */
    private fun downloadFileCall(): Request.Builder {
        val builder = Request.Builder()

        builder.url(httpBuilder!!.build().toString() + param!!)
        builder.headers(Headers.of(mHeadersMap))
        builder.tag(REQUEST_TAG)

        return builder
    }

    /**
     * UPLOAD file call
     * @return
     */
    private fun uploadFileCall(): Request.Builder {
        val builder = Request.Builder()

        val stFileName = more!![FILENAME] as String?
        val file = File(stFileName!!)

        builder.url(httpBuilder!!.build().toString() + param!!)
        builder.headers(Headers.of(mHeadersMap))

        if (isMultipart) {
            val builderUpload = MultipartBody.Builder()

            builderUpload.setType(MultipartBody.FORM)

            builderUpload.addFormDataPart("Content-type", "image/png")
            builderUpload.addFormDataPart(
                "file",
                attachmentFileName,
                RequestBody.create(MediaType.parse("image/png"), file)
            )

            val formBody = builderUpload.build()
            builder.method("POST", RequestBody.create(MediaType.parse(CONTENT_TYPE), formBody.toString()))
        } else {
            builder.put(RequestBody.create(MediaType.parse("image"), file))
        }
        //                    builder.post(formBody);
        builder.tag(REQUEST_TAG)

        return builder
    }

    private fun bodyToString(request: Request): String {

        try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body()!!.writeTo(buffer)
            return buffer.readUtf8()
        } catch (e: IOException) {
            return "did not work"
        }

    }
}