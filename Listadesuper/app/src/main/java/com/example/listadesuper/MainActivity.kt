package com.example.listadesuper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listadesuper.adapter.ItemsAdapter
import com.example.listadesuper.databinding.ActivityMainBinding
import com.example.listadesuper.model.ItemModel
import com.example.listadesuper.utils.SharedPreferencesUtils.Companion.getItemsSharedPreferences
import com.example.listadesuper.utils.SwipeToDeleteCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private lateinit var itemList: ArrayList<ItemModel>
    private lateinit var itemsAdapter: ItemsAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        itemsAdapter = ItemsAdapter(this)
        getItems()
        binding.btnAdd.setOnClickListener {
            addItems(ItemModel(binding.editTxt.text.toString(), false))
        }
        initUi()
    }

    private fun initUi() {
        binding.list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = itemsAdapter
        initSwipe(binding.list)
    }

    private fun initSwipe(list: RecyclerView) {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.list.adapter as ItemsAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.list)
    }

    private fun getItems() {
        val json = getItemsSharedPreferences(this).getString("items", null)
        val type: Type = object : TypeToken<ArrayList<ItemModel>>() {}.type
        itemList = Gson().fromJson(json, type) ?: arrayListOf()
        itemsAdapter.addItems(itemList)
    }

    private fun addItems(value: ItemModel) {
        if (value.name.isNotEmpty()) {
            itemList.add(value)
            getItemsSharedPreferences(this).edit().putString("items", Gson().toJson(itemList)).apply()
            println(Gson().toJson(itemList))
            itemsAdapter.addItem(value)
            binding.editTxt.setText("")
        } else {
            Toast.makeText(this.applicationContext, "Dato Invalido", Toast.LENGTH_SHORT).show()
        }
    }
}
