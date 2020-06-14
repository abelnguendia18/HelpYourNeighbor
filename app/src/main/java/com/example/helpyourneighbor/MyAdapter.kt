package com.example.helpyourneighbor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlin.coroutines.coroutineContext

class MyAdapter(val arrayList: ArrayList<Announcement>, val context : Context) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    class  ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(announcement: Announcement, context: Context){
            itemView.titleTv.text = announcement.titleAnnouncement
            itemView.descriptionTv.text = announcement.descriptionAnnouncement
            //itemView.imageIv.setImageResource(model.image)
            Glide.with(context).load(announcement.imagePath).into(itemView.imageIv)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return arrayList.size
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.bindItems(arrayList[position], context)
        holder.itemView.setOnClickListener{
           val titel :String = arrayList[position].titleAnnouncement
            Log.d("samsam", titel)
        }
    }

}