package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.nsutBranch
import com.example.zeolous.R

class nsutBranchAdapter(private val childList: List<nsutBranch>) :
    RecyclerView.Adapter<nsutBranchAdapter.ChildViewHolder>() {

    private lateinit var mListener : onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    inner class ChildViewHolder(itemView: View,listner: nsutBranchAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.top_Title_br)
        val image: ImageView = itemView.findViewById(R.id.top_IMG_br)

        init {
            itemView.setOnClickListener{
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.nsut_branch_recycler, parent, false)
        return ChildViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
           val Branch = childList[position]
        holder.title.text = Branch.branch
        holder.image.setImageResource(Branch.image)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

}