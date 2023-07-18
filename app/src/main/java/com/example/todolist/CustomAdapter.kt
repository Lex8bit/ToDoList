package com.example.todolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: MutableList<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.titleView.text = ItemsViewModel.title
        holder.descriptionView.text = ItemsViewModel.description
        holder.numberView.text = ItemsViewModel.number.toString()


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun addItem(item: ItemsViewModel){
        mList.add(item)
        // Update data
        notifyDataSetChanged()
    }

    // Holds the views for adding it to text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val titleView: TextView = itemView.findViewById(R.id.item_recycler_title)
        val descriptionView: TextView = itemView.findViewById(R.id.item_recycler_description)
        val numberView: TextView = itemView.findViewById(R.id.item_recycler_number)

    }
}