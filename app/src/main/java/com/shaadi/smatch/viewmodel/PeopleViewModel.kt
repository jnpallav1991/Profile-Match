package com.shaadi.smatch.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.shaadi.smatch.database.People
import com.shaadi.smatch.service.repository.ResultRepository

class PeopleViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = ResultRepository(application)
    private var allPeopleList: LiveData<List<People>>? = repository.getPeopleDetails()

    fun getPeopleList(): LiveData<List<People>>? {
        return allPeopleList
    }

    fun updatePeople(people: People) {
        repository.updatePeople(people)
    }

    fun callService()
    {
        repository.getPeopleList()
    }

}