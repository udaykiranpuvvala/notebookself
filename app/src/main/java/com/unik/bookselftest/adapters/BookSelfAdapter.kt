package com.unik.bookselftest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unik.bookselftest.R
import com.unik.bookselftest.model.BooksModel

class BookSelfAdapter(
    val context: Context,
    val booksArrayList: ArrayList<BooksModel>
) :
    RecyclerView.Adapter<BookSelfAdapter.MyHolder>() {
    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBookTitle: TextView = itemView.findViewById(R.id.txtBookTitle)
        val ivBookSelf: ImageView = itemView.findViewById(R.id.ivBookSelf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_bookself, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
//        if (booksArrayList.get(position).categoryId.equals(categoryId)) {
            holder.txtBookTitle.text = booksArrayList.get(position).bookName
            when (booksArrayList.get(position).cover) {
                1 -> holder.ivBookSelf.setImageResource(R.drawable.self_one)
                2 -> holder.ivBookSelf.setImageResource(R.drawable.self_two)
                3 -> holder.ivBookSelf.setImageResource(R.drawable.self_three)
                4 -> holder.ivBookSelf.setImageResource(R.drawable.self_four)
                5 -> holder.ivBookSelf.setImageResource(R.drawable.self_five)
                6 -> holder.ivBookSelf.setImageResource(R.drawable.self_six)
            }
//        }
    }

    override fun getItemCount(): Int {
        return booksArrayList.size
    }
}