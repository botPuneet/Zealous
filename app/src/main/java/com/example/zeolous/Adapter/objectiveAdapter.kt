package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.Best_selling_course
import com.example.zeolous.R

class objectiveAdapter (private val bestcourse : ArrayList<String>) : RecyclerView.Adapter<objectiveAdapter.MyViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.objective_recycler,parent,false)
        return MyViewHolder(view,mListener)
    }

    override fun getItemCount(): Int {
        return bestcourse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bestcourse[position]
        holder.top_Title.text = currentItem

    }
    class MyViewHolder(itemView: View, listener: objectiveAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val top_Title : TextView = itemView.findViewById(R.id.objective)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}