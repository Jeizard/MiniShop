package com.jeizard.minishop.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeizard.minishop.R
import com.jeizard.minishop.databinding.FooterItemGoodBinding
import com.jeizard.minishop.databinding.ItemGoodBacketBinding
import com.jeizard.minishop.databinding.ItemGoodBinding
import com.jeizard.minishop.entities.Good

class GoodBasketAdapter() :
    RecyclerView.Adapter<GoodBasketAdapter.ViewHolder>() {
    var goodsList: List<Good> = emptyList()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemGoodBacketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            good: Good
        ) {
            binding.nameTextView.text = good.name
            binding.priceTextView.text = good.price.toString() + " BYN"
            binding.countTextView.text =
                "X " + good.count.toString() + " = " + (good.price * good.count).toString()
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGoodBacketBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }



    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        viewHolder.bind(goodsList[position])
    }

    override fun getItemCount(): Int = goodsList.size
}