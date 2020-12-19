package com.codemasters.tmdb.ui.wish_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codemasters.tmdb.R
import com.codemasters.tmdb.data.model.ContentDetails
import com.codemasters.tmdb.databinding.RowWishListBinding
import com.codemasters.tmdb.utils.toPosterImageUrl

class WishListAdapter(
    val onclick: (ContentDetails) -> Unit
) : ListAdapter<ContentDetails, WishListAdapter.ViewHolder>(HorizontalListAdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_wish_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder._binding.apply {
            tvTitle.text = item.title ?: item.name
            tvRating.text = item.vote_average.toString()
            tvOverView.text = item.overview
            ivPoster.load(item.poster?.toPosterImageUrl())
        }
    }

    class HorizontalListAdapterDiffUtil : DiffUtil.ItemCallback<ContentDetails>() {
        override fun areItemsTheSame(oldItem: ContentDetails, newItem: ContentDetails): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ContentDetails, newItem: ContentDetails): Boolean {
            return oldItem.poster == newItem.poster
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val _binding = RowWishListBinding.bind(view)

        init {
            view.setOnClickListener {
                onclick(getItem(adapterPosition))
            }
        }
    }
}