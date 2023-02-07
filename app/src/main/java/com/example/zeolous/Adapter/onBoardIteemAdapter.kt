package com.example.zeolous.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zeolous.Models.onBoardItem
import com.example.zeolous.R

class onBoardItemAdapter(private val onBoardItems: List<onBoardItem>)  :
  RecyclerView.Adapter<onBoardItemAdapter.onBoardItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): onBoardItemViewHolder {
        return onBoardItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboard_item,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: onBoardItemViewHolder, position: Int) {
       holder.bind(onBoardItems[position])
    }

    override fun getItemCount(): Int {
        return  onBoardItems.size
    }

    inner class onBoardItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val imageOnboarding = view.findViewById<ImageView>(R.id.image_on_board)
        private val textTitle = view.findViewById<TextView>(R.id.onBoard_text_title)
        private val textDescp = view.findViewById<TextView>(R.id.onBoard_text_descp)

        fun bind(onBoardItem: onBoardItem){
            imageOnboarding.setImageResource(onBoardItem.onBoardImg)
            textTitle.text = onBoardItem.textTitle
            textDescp.text = onBoardItem.textDescp
        }


    }
}