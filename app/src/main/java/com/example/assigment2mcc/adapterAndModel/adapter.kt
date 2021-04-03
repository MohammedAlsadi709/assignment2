package com.example.assigment2mcc.adapterAndModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assigment2mcc.R
import kotlinx.android.synthetic.main.item_design.view.*

class adapter(var activity: Activity, var data: MutableList<model>,var onClick : onItemClick) :
    RecyclerView.Adapter<adapter.myViewHolder>() {

    class myViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val name = root.txtName
        val details = root.txtDetails
        val image = root.image
        val item1 = root.item1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val item = LayoutInflater.from(activity).inflate(R.layout.item_design, parent, false)
        return myViewHolder(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.details.text = data[position].details

        Glide.with(activity).
        load(data[position].image).
        into(holder.image)

        holder.item1.setOnClickListener {
            onClick.onClickItem(holder.adapterPosition,data)

        }
    }

    interface onItemClick{
        fun onClickItem(p:Int,data: MutableList<model>)
    }

}