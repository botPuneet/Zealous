package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.recomendedChild
import com.example.zeolous.R

class recomChildAdapter (private val childList: List<recomendedChild>) :
    RecyclerView.Adapter<recomChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.childLogoIv)
        val title: TextView = itemView.findViewById(R.id.childTitleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recomchilditem, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.logo.setImageResource(childList[position].logo)
        holder.title.text = childList[position].title
    }

    override fun getItemCount(): Int {
        return childList.size
    }

}