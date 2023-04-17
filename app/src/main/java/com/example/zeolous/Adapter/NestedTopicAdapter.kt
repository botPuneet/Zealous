package com.example.zeolous.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.nestedTopic
import com.example.zeolous.R


class NestedTopicAdapter (val context : Context, private val parentList: List<nestedTopic>) :
    RecyclerView.Adapter<NestedTopicAdapter.ParentViewHolder>() {

    private lateinit var mListener : NestedTopicAdapter.onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }

    }

    fun setOnItemClickListener(listener: NestedTopicAdapter.onItemClickListener){
        mListener = listener
    }

    inner class ParentViewHolder(itemView: View,listener: NestedTopicAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val titleTv: TextView = itemView.findViewById(R.id.tittlen)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerObjectivenn)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.n_topic_recycler, parent, false)
        return ParentViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val parentItem = parentList[position]

        holder.titleTv.text = parentItem.topic

        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        val adapter = SubTopicAdapter(parentItem.mList)
        holder.childRecyclerView.adapter = adapter


    }

    override fun getItemCount(): Int {
        return parentList.size
    }
}