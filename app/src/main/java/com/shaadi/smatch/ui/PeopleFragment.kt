package com.shaadi.smatch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shaadi.smatch.R
import com.shaadi.smatch.adapter.PeopleListAdapter
import com.shaadi.smatch.database.People
import com.shaadi.smatch.viewmodel.PeopleViewModel
import kotlinx.android.synthetic.main.fragment_people.*

class PeopleFragment : Fragment(), PeopleListAdapter.OnItemClick {

    private lateinit var adapter: PeopleListAdapter
    private lateinit var peopleViewModel: PeopleViewModel
    private lateinit var peopleList: ArrayList<People?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        peopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity!!)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewPeople.layoutManager = layoutManager

        peopleList = ArrayList()
        adapter = PeopleListAdapter(peopleList, this, context!!)
        recyclerViewPeople.adapter = adapter


        swipeViewRefresh.setOnRefreshListener {
            getServiceCall()
        }

        getPeopleList()
    }

    private fun getServiceCall() {
        peopleViewModel.callService()
    }

    private fun getPeopleList()
    {
        swipeViewRefresh.isRefreshing = true
        peopleViewModel.getPeopleList()!!.observe(viewLifecycleOwner,
            Observer { response ->

                swipeViewRefresh.isRefreshing = false
                if (!response.isNullOrEmpty()) {
                    adapter.addPeopleList(response)
                }

            })
    }

    override fun onClick(people: People) {
        peopleViewModel.updatePeople(people)
    }


}
