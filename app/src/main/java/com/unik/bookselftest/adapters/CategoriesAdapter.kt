package com.unik.bookselftest.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unik.bookselftest.R
import com.unik.bookselftest.model.Categories
import com.unik.bookselftest.utilities.OnItemClickListener

class CategoriesAdapter(
    val context: Context, val categoriesArrayList: ArrayList<Categories>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoriesAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_categories, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.txtCategory.text = categoriesArrayList.get(position).categoryTitle

        if(categoriesArrayList.get(position).selected != 0){
            holder.txtCategory.setTextColor(Color.parseColor("#C1B22A"))
        }else{
            holder.txtCategory.setTextColor(Color.parseColor("#000000"))
        }
        holder.itemView.setOnClickListener {
            holder.txtCategory.setTextColor(Color.parseColor("#C1B22A"))
            listener.onItemClick(categoriesArrayList.get(position),position)
        }
    }

    override fun getItemCount(): Int {
        return categoriesArrayList.size
    }
}