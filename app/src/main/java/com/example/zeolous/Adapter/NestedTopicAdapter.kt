package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.nestedTopic
import com.example.zeolous.R

class NestedTopicAdapter (private val parentList: List<nestedTopic>) :
    RecyclerView.Adapter<NestedTopicAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv: TextView = itemView.findViewById(R.id.tittlen)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerObjectivenn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.n_topic_recycler, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = parentList[position]

        holder.titleTv.text = parentItem.topic

        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 3)
        val adapter = SubTopicAdapter(parentItem.mList)
        holder.childRecyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return parentList.size
    }
}