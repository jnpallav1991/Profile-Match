package com.shaadi.smatch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaadi.smatch.R
import com.shaadi.smatch.database.People
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview_people_list.view.*

class PeopleListAdapter(
    private val arrayList: ArrayList<People?>,
    private val onItemClick: OnItemClick,
    private val context: Context
) :
    RecyclerView.Adapter<PeopleListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.recyclerview_people_list, viewGroup,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position]!!)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(people: People) {

            Picasso.get()
                .load(people.picture)
                .placeholder(R.drawable.ic_holder)
                .error(R.drawable.ic_holder)
                .into(itemView.imagePeople)

            itemView.txtName.text = people.name
            itemView.txtAddress.text = people.location

            if (people.status == null) {
                itemView.group.visibility = View.VISIBLE
                itemView.resultButton.visibility = View.GONE
            } else {
                if (people.status!!) {
                    itemView.resultButton.text = context.getString(R.string.member_accepted)
                    itemView.resultButton.isEnabled = true
                } else {
                    itemView.resultButton.text = context.getString(R.string.member_declined)
                    itemView.resultButton.isEnabled = false


                }
                itemView.group.visibility = View.GONE
                itemView.resultButton.visibility = View.VISIBLE

            }
            itemView.declineButton.setOnClickListener {
                people.status = false
                onItemClick.onClick(people)
            }

            itemView.acceptButton.setOnClickListener {
                people.status = true
                onItemClick.onClick(people)
            }
        }
    }

    fun addPeopleList(peopleList: List<People?>) {

        arrayList.clear()
        arrayList.addAll(peopleList)
        notifyDataSetChanged()
    }

    interface OnItemClick {

        fun onClick(people: People)

    }
}