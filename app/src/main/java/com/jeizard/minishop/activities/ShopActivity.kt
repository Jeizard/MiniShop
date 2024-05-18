package com.jeizard.minishop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeizard.minishop.adapters.GoodAdapter
import com.jeizard.minishop.databinding.ActivityShopBinding
import com.jeizard.minishop.entities.services.GoodService

class ShopActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopBinding
    private lateinit var adapter: GoodAdapter
    private val goodService: GoodService = GoodService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView: RecyclerView = binding.goodRecyclerView
        adapter = GoodAdapter(
            onButtonClicked = {
                val goodsToSend = adapter.goodsList.filter { it.count != 0 }
                if (goodsToSend.isNotEmpty()) {
                    val intent = Intent(this, BasketActivity::class.java)
                    intent.putParcelableArrayListExtra("Selected Goods List", ArrayList(goodsToSend))
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Ни один товар не выбран!", Toast.LENGTH_SHORT).show()
                }
            }
        )
        adapter.goodsList =  goodService.goods
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}