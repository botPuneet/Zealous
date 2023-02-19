package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.recomendedParent
import com.example.zeolous.R

class recomParentAdapter (private val parentList: List<recomendedParent>) :
    RecyclerView.Adapter<recomParentAdapter.ParentViewHolder>() {



    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv: TextView = itemView.findViewById(R.id.parentTitleTv)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.langRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recomparentitem, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentItem = parentList[position]
        holder.titleTv.text = parentItem.title

        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 3)
        val adapter = recomChildAdapter(parentItem.mList)
        holder.childRecyclerView.adapter = adapter
    }

    override fun getItemCount(): Int {
        return parentList.size
    }
}