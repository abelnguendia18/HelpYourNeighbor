package com.example.helpyourneighbor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlin.coroutines.coroutineContext

class MyAdapter(val arrayList: MutableList<Announcement>, val context : Context, var itemClickListener: OnItemClickListener) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    class  ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(announcement: Announcement, clickListener : OnItemClickListener){
            itemView.titleTv.text = announcement.status
            itemView.descriptionTv.text = announcement.categoryAnnouncement
            itemView.priceTv.text = announcement.priceAnnouncement+"Euro"
            itemView.cityTv.text = announcement.ownerAddress
            //itemView.imageIv.setImageResource(model.image)
            //Glide.with(context).load("https://www.neuhauschocolates.com/4a2225/globalassets/neuhaus_images/5020497_thumbnail-coeurpraline.png?height=630&width=1200&mode=crop&quality=80").into(itemView.imageIv)
/*            Picasso.get().load("https://www.neuhauschocolates.com/4a2225/globalassets/neuhaus_images/5020497_thumbnail-coeurpraline.png?height=630&width=1200&mode=crop&quality=80")
                .into(itemView.imageIv);*/

            itemView.setOnClickListener{
                clickListener.onItemClick(announcement)
            }

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
        Log.d("sonson", "part2: il y a ${arrayList.size} elements dans la db")
        holder.bindItems(arrayList[position], itemClickListener)
        /*holder.itemView.setOnClickListener{
           val img :String = arrayList[position].imagePath
            Log.d("samsam", img)
            //notifyItemChanged(holder.adapterPosition)
        }*/
    }

    interface OnItemClickListener{
        fun onItemClick(announcement: Announcement)
    }

}