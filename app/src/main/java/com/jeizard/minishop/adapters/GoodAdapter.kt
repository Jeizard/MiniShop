package com.jeizard.minishop.adapters

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeizard.minishop.R
import com.jeizard.minishop.databinding.FooterItemGoodBinding
import com.jeizard.minishop.databinding.HeaderItemGoodBinding
import com.jeizard.minishop.databinding.ItemGoodBinding
import com.jeizard.minishop.entities.Good

private const val VIEW_TYPE_HEADER = -1
private const val VIEW_TYPE_ITEM = 0
private const val VIEW_TYPE_FOOTER = 1
class GoodAdapter(private val onButtonClicked: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var expandableGoodIndex: Int = -1
    var goodsList: List<Good> = emptyList()

    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemGoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            good: Good
        ) {
            binding.nameTextView.text = good.name
            binding.priceTextView.text= good.price.toString() + " BYN"
            binding.descriptionTextView.text = good.description
            Glide.with(binding.root).load(good.imageUrl).placeholder(R.drawable.good_placeholder).into(binding.imageView)
            if(good.count != 0){
                binding.checkBox.isChecked = true
                binding.addToBasketButton.visibility = View.GONE
                binding.goodCountConstrain.visibility = View.VISIBLE
            }
            else{
                binding.checkBox.isChecked = false
                binding.addToBasketButton.visibility = View.VISIBLE
                binding.goodCountConstrain.visibility = View.GONE
            }

            binding.goodCountEditText.text = good.count.toString()
        }
    }

    class FooterViewHolder(private val binding: FooterItemGoodBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            selectedItemCount: Int,
            onButtonClicked: () -> Unit
        ) {
            binding.footerTextView.text = "Выбрано товаров: $selectedItemCount"
            binding.button.setOnClickListener(View.OnClickListener {
                onButtonClicked()
            })
        }
    }

    class HeaderViewHolder(private val binding: HeaderItemGoodBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = HeaderItemGoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_ITEM -> {
                val binding = ItemGoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolder(binding)
            }
            VIEW_TYPE_FOOTER -> {
                val binding = FooterItemGoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FooterViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        if (viewHolder is ViewHolder) {
            val good = goodsList[position]

            viewHolder.bind(good)

            viewHolder.binding.checkBox.isChecked = good.count != 0

            viewHolder.binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                viewHolder.binding.checkBox.tag = position
                if (buttonView != null) {
                    if(buttonView.isShown){
                        val position = buttonView.tag as? Int
                        position?.let { pos ->
                            val item = goodsList[pos]
                            if (isChecked) {
                                item.count = 1
                            } else {
                                item.count = 0
                            }
                        }
                        buttonView?.post {
                            notifyItemChanged(position!!)
                            notifyItemChanged(itemCount - 1)
                        }
                    }
                }
            }
            viewHolder.binding.root.setOnClickListener(View.OnClickListener {
                if(expandableGoodIndex != position){
                    expandableGoodIndex = position
                }
                else{
                    expandableGoodIndex = -1
                }
                notifyDataSetChanged()
            })
            viewHolder.binding.constraint.visibility =
                if (position == expandableGoodIndex) View.VISIBLE else View.GONE

            viewHolder.binding.addToBasketButton.setOnClickListener(View.OnClickListener {
                good.count = 1
                notifyItemChanged(position)
                notifyItemChanged(itemCount - 1)
            })
            viewHolder.binding.addButton.setOnClickListener(View.OnClickListener {
                if(good.count < 99) {
                    good.count += 1
                    notifyItemChanged(position)
                }
            })
            viewHolder.binding.removeButton.setOnClickListener(View.OnClickListener {
                if(good.count != 0) {
                    good.count -= 1
                    notifyItemChanged(position)
                    notifyItemChanged(itemCount - 1)
                }
            })
        }
        else if (viewHolder is FooterViewHolder) {
            val selectedItemCount = goodsList.count{ it.count != 0}
            viewHolder.bind(selectedItemCount, onButtonClicked)
        }
    }

    override fun getItemCount(): Int = goodsList.size + 1
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            VIEW_TYPE_FOOTER
        } else if(position == 0){
            VIEW_TYPE_HEADER
        }
        else {
            VIEW_TYPE_ITEM
        }
    }
}