package com.allens.model_http.impl

import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 11:47
 * @Version:        1.0
 */
interface ApiService{
    @GET
    fun doGet(@HeaderMap headers: HashMap<String, String>, @Url url: String): Observable<ResponseBody>


    @FormUrlEncoded
    @POST("{path}")
    fun doPost(
        @Path(
            value = "path",
            encoded = true
        ) urlPath: String, @HeaderMap headers: HashMap<String, String>, @FieldMap map: HashMap<String, Any>
    ): Observable<ResponseBody>


    @POST("{path}")
    fun doBody(
        @Path(
            value = "path",
            encoded = true
        ) urlPath: String, @HeaderMap headers: HashMap<String, String>, @Body body: RequestBody
    ): Observable<ResponseBody>


    @DELETE
    fun doDelete(@Url url: String, @HeaderMap headers: HashMap<String, String>, @QueryMap maps: Map<String, Any>): Observable<ResponseBody>


    @PUT
    fun doPut(@Url url: String, @HeaderMap headers: HashMap<String, String>, @FieldMap maps: HashMap<String, Any>): Observable<ResponseBody>

    @Streaming
    @GET
    fun downloadFile(@Header("RANGE") start: String, @Url url: String): Observable<ResponseBody>


    @PUT
    fun uploadByPut(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>,
        @Body body: RequestBody
    ): Observable<ResponseBody>

    @POST
    fun upload(
        @Url url: String,
        @HeaderMap headers: HashMap<String, String>,
        @Body body: RequestBody
    ): Observable<ResponseBody>

}