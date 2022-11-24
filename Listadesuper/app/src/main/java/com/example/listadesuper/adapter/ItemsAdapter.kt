package com.example.listadesuper.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.listadesuper.R
import com.example.listadesuper.model.ItemModel
import com.example.listadesuper.utils.SharedPreferencesUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.itemlist.view.*

class ItemsAdapter(private val context: Context) : RecyclerView.Adapter<ItemsAdapter.VH>() {

    private val originalList = ArrayList<ItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(originalList[position])
        holder.itemView.findViewById<CheckBox>(R.id.cbBuyed).setOnClickListener {
            val item = originalList[position]
            item.status = !item.status
            SharedPreferencesUtils.getItemsSharedPreferences(context).edit().putString("items", Gson().toJson(originalList)).apply()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = originalList.size

    fun addItems(items: ArrayList<ItemModel>) {
        originalList.clear()
        originalList.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: ItemModel) {
        originalList.add(item)
        notifyItemInserted(originalList.size)
    }

    fun removeAt(position: Int) {
        originalList.removeAt(position)
        SharedPreferencesUtils.getItemsSharedPreferences(context).edit().putString("items", Gson().toJson(originalList)).apply()
        notifyItemRemoved(position)
    }

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
    ) {

        fun bind(item: ItemModel) = with(itemView) {
            rowName.text = item.name
            cbBuyed.isChecked = item.status
            if (cbBuyed.isChecked) {
                cbBuyed.text = "Comprado"
            } else {
                cbBuyed.text = "No Comprado"
            }
        }
    }
}
