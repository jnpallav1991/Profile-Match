package com.shaadi.smatch.service.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.shaadi.smatch.AppApplication
import com.shaadi.smatch.database.People
import com.shaadi.smatch.database.PeopleDao
import com.shaadi.smatch.database.ShaadiDatabase
import com.shaadi.smatch.model.ResponseSBody
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultRepository(application: Application) {

    private var peopleDao: PeopleDao?
    private var peopleDetailList: LiveData<List<People>>? = null

    init {
        val database: ShaadiDatabase = ShaadiDatabase.getDatabase(
            application.applicationContext
        )
        peopleDao = database.getPeopleDao()
        peopleDetailList = peopleDao?.getPeopleDetails()
        getPeopleList()
    }

    fun getPeopleList() {
        AppApplication.getApiClient().getRestInterface().getPeopleList()
            .enqueue(object : Callback<ResponseSBody> {

                override fun onResponse(
                    call: Call<ResponseSBody>,
                    responseSsc: Response<ResponseSBody>
                ) {
                    if (responseSsc.isSuccessful) {
                        updateDatabase(responseSsc.body())
                    }
                }

                override fun onFailure(call: Call<ResponseSBody>, t: Throwable) {

                    //response.value = null

                }
            })

    }

    private fun updateDatabase(responseSBody: ResponseSBody?) {
        if (responseSBody != null) {
            if (!responseSBody.results.isNullOrEmpty()) {
                for (p in responseSBody.results) {
                    val people = People()
                    people.name =
                        "${p?.name?.title}. ${p?.name?.first} ${p?.name?.last} (${p?.dob?.age})"
                    people.location = "${p?.location?.city} , ${p?.location?.country}"
                    people.loginId = p?.login?.uuid
                    people.picture = p?.picture?.large
                    insertDb(people)
                }
            }
        }
    }

    private fun insertDb(people: People) {
        doAsync {
            val peopleFromDb = peopleDao?.getPeopleById(people.loginId!!)
            if (peopleFromDb == null) {
                peopleDao?.insertPeople(people)
            }
        }

    }

    fun getPeopleDetails(): LiveData<List<People>>? {
        return peopleDetailList
    }

    fun updatePeople(people: People) {
        doAsync {
            peopleDao?.updatePeople(people.status, people.loginId)
        }
    }
}