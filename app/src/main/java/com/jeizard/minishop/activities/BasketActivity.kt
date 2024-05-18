package com.jeizard.minishop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeizard.minishop.R
import com.jeizard.minishop.adapters.GoodAdapter
import com.jeizard.minishop.adapters.GoodBasketAdapter
import com.jeizard.minishop.databinding.ActivityBasketBinding
import com.jeizard.minishop.entities.Good

class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    private lateinit var adapter: GoodBasketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent: Intent = intent
        val selectedGoods = intent.getParcelableArrayListExtra<Good>("Selected Goods List")

        val recyclerView: RecyclerView = binding.goodRecyclerView
        adapter = GoodBasketAdapter()
        if (selectedGoods != null) {
            adapter.goodsList =  selectedGoods.toList()
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}