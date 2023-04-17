package com.example.zeolous.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.Topic
import com.example.zeolous.Models.nestedTopic
import com.example.zeolous.Models.nestedTopicX
import com.example.zeolous.R
import com.example.zeolous.databinding.TopicRecyler2Binding

class topicX (private val mTopic : ArrayList<nestedTopicX>) : RecyclerView.Adapter<topicX.MyViewHolder>(){
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = TopicRecyler2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view,mListener)
    }

    override fun getItemCount(): Int {
        return mTopic.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mTopic[position])

    }
   @SuppressLint("SuspiciousIndentation")
   inner class MyViewHolder(private val binding : TopicRecyler2Binding, listener: topicX.onItemClickListener) : RecyclerView.ViewHolder(binding.root) {

       private fun setChildRecycler(child : List<Topic>){
           binding.obRR.visibility = View.VISIBLE
            val adapter1 = SubTopicAdapter(child)
            binding.obRR.adapter = adapter1
       }

        init {
             binding.obRR.setHasFixedSize(true)
            binding.obRR.layoutManager = LinearLayoutManager(binding.root.context)

            binding.button3.setOnClickListener {
                mTopic[adapterPosition].isopen = !mTopic[adapterPosition].isopen
                notifyItemChanged(adapterPosition)

            }


                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }


        }

        @SuppressLint("SuspiciousIndentation")
        fun bind(parentitem : nestedTopicX){
          binding.topic555.text = parentitem.topic

            when(true){
                parentitem.isopen->{
                    setChildRecycler(parentitem.mList)
                }
                else -> {
                    binding.obRR.visibility = View.GONE
                }
            }
        }
    }
}