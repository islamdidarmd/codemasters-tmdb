package com.codemasters.tmdb.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codemasters.tmdb.R
import com.codemasters.tmdb.data.model.Movie
import com.codemasters.tmdb.data.model.Trending
import com.codemasters.tmdb.data.model.TvSeries
import com.codemasters.tmdb.databinding.RowMoviePosterBinding
import com.codemasters.tmdb.utils.toPosterImageUrl

class HorizontalListAdapter<T>(
    val onclick: (T) -> Unit
) : ListAdapter<T, HorizontalListAdapter<T>.ViewHolder>(HorizontalListAdapterDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_movie_poster, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder._binding.apply {
            when (item) {
                is Movie -> {
                    tvTitle.text = item.title
                    tvReleaseDate.text = item.releaseDate
                    tvVoteCount.text = item.voteCount.toString()
                    ivPoster.load(item.poster?.toPosterImageUrl()){
                        error(R.mipmap.ic_launcher)
                        fallback(R.mipmap.ic_launcher)
                        placeholder(R.mipmap.ic_launcher)
                    }
                }
                is TvSeries -> {
                    tvTitle.text = item.title
                    tvReleaseDate.text = item.releaseDate
                    tvVoteCount.text = item.voteCount.toString()
                    ivPoster.load(item.poster?.toPosterImageUrl()){
                        error(R.mipmap.ic_launcher)
                        fallback(R.mipmap.ic_launcher)
                        placeholder(R.mipmap.ic_launcher)
                    }
                }

                is Trending -> {
                    tvTitle.text = item.title
                    tvReleaseDate.text = item.releaseDate
                    tvVoteCount.text = item.voteAverage.toString()
                    ivPoster.load(item.poster?.toPosterImageUrl()){
                        error(R.mipmap.ic_launcher)
                        fallback(R.mipmap.ic_launcher)
                        placeholder(R.mipmap.ic_launcher)
                    }
                }
            }
        }
    }

    class HorizontalListAdapterDiffUtil<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return when (oldItem) {
                is Movie -> oldItem.title == (newItem as Movie).title
                is TvSeries -> oldItem.title == (newItem as TvSeries).title
                is Trending -> oldItem.title == (newItem as TvSeries).title
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return when (oldItem) {
                is Movie -> oldItem.poster == (newItem as Movie).poster
                is TvSeries -> oldItem.poster == (newItem as TvSeries).poster
                is Trending -> oldItem.poster == (newItem as TvSeries).poster
                else -> false
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val _binding = RowMoviePosterBinding.bind(view)

        init {
            view.setOnClickListener {
                onclick(getItem(adapterPosition))
            }
        }
    }
}