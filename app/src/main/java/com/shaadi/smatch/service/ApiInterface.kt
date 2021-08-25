package com.shaadi.smatch.service

import com.shaadi.smatch.model.ResponseSBody
import com.shaadi.smatch.utils.Constant
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET(Constant.SubUrl.RESULTS)
    fun getPeopleList(): Call<ResponseSBody>

}