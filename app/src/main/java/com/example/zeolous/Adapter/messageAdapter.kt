package com.example.zeolous.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zeolous.Models.message
import com.example.zeolous.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class messageAdapter( val context: Context,  val messages : ArrayList<message>?) : RecyclerView.Adapter<RecyclerView.ViewHolder?>()
{

    val ITEM_RECIEVE_msg = 11
    val ITEM_Sent_msg = 21


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType==11) {
           val view: View =
               LayoutInflater.from(context).inflate(R.layout.chat_from_row, parent, false)
           return recievedMsgHolder(view)
       }else{
           val view: View =
               LayoutInflater.from(context).inflate(R.layout.chat_to_row, parent, false)
           return SentMsgHolder(view)
       }
    }

    override fun getItemCount(): Int {
      return  messages!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmsg = messages!![position]

       if(holder.javaClass ==SentMsgHolder::class.java) {
           val viewholder = holder as SentMsgHolder
           if (currentmsg.type == "IMG") {
               holder.sentmsg.visibility = View.GONE
               holder.sentImg.visibility = View.VISIBLE
               Glide.with(context).load(currentmsg.Image).into(holder.sentImg)
           } else {
               holder.sentmsg.visibility = View.VISIBLE
               holder.sentImg.visibility = View.GONE
               holder.sentmsg.text = currentmsg.message.toString()
           }

       }else {
           val viewholder = holder as recievedMsgHolder
           if (currentmsg.type == "IMG") {
               holder.recieveMsg.visibility = View.GONE
               holder.receiveImg.visibility = View.VISIBLE
               Glide.with(context).load(currentmsg.Image).into(holder.receiveImg)
           } else {
               holder.recieveMsg.visibility = View.VISIBLE
               holder.receiveImg.visibility = View.GONE
               holder.recieveMsg.text = currentmsg.message.toString()
           }
       }

       }


    override fun getItemViewType(position: Int): Int {
        val currentmsg = messages!![position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmsg.senderID)) {

                    return ITEM_Sent_msg

        } else {

                return ITEM_RECIEVE_msg

        }
    }


    class  SentMsgHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val sentmsg = itemView.findViewById<TextView>(R.id.textview_to_row)
        val sentImg= itemView.findViewById<ImageView>(R.id.imageView4)
    }


    inner class  recievedMsgHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val recieveMsg = itemView.findViewById<TextView>(R.id.textview_from_row)
        val receiveImg= itemView.findViewById<ImageView>(R.id.imageView5)
    }


}