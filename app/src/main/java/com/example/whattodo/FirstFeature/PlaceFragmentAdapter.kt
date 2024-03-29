package com.example.whattodo.FirstFeature

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.databinding.ItemCategoryBinding
import com.example.whattodo.datas.Category

class PlaceFragmentAdapter(
    val categoires: List<Category>,
    private val itemClickListener: ItemClickListener? = null) : RecyclerView.Adapter<PlaceFragmentAdapter.PlaceFragmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceFragmentViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return PlaceFragmentViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PlaceFragmentViewHolder, position: Int) {
        val category = categoires[position]
        holder.bind(category)
        holder.itemView.setOnClickListener { itemClickListener?.onClick(category) }
    }

    override fun getItemCount(): Int {
        return categoires.size
    }


    inner class PlaceFragmentViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                binding.categoryImageView.setImageResource(category.image)
                binding.categoryTextView.text = category.title
            }

        }
    }
    /* 오버라이드 해야할 인터페이스 함수 */
    interface ItemClickListener {
        fun onClick(category: Category)
    }
}