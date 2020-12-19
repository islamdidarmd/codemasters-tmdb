package com.codemasters.tmdb.ui.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codemasters.tmdb.R
import com.codemasters.tmdb.data.model.Movie
import com.codemasters.tmdb.data.model.StatefulData
import com.codemasters.tmdb.data.model.Trending
import com.codemasters.tmdb.data.model.TvSeries
import com.codemasters.tmdb.databinding.FragmentDiscoverBinding
import com.codemasters.tmdb.ui.widgets.MultiStateLayout

class DiscoverFragment : Fragment(R.layout.fragment_discover) {
    private var _binding: FragmentDiscoverBinding? = null
    private val viewModel by viewModels<DiscoverViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDiscoverBinding.bind(view)

        if (savedInstanceState == null) {
            setUpUI()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
         //   setUpUI()
        }
    }

    private fun setUpUI() {
        _binding?.apply {
            appBarLayout.toolbar.title = getString(R.string.discover)

            setUpPopularMovies()
            setUpPopularTvSeries()
            setUpTrending()
        }
    }

    private fun setUpPopularMovies() {
        viewModel.getPopularMovies()
            .observe(viewLifecycleOwner) { response ->
                when (response?.status) {
                    StatefulData.Status.SUCCESS -> {
                        populatePopularMovies(response.data?.results)
                    }
                    StatefulData.Status.ERROR -> {
                        _binding?.layoutPopularMoviesState?.setState(MultiStateLayout.State.EMPTY)
                    }
                    StatefulData.Status.LOADING -> {
                        _binding?.layoutPopularMoviesState?.setState(MultiStateLayout.State.LOADING)
                    }
                    null -> {
                        _binding?.layoutPopularMoviesState?.setState(MultiStateLayout.State.EMPTY)
                    }
                }
            }
    }

    private fun populatePopularMovies(results: List<Movie>?) {
        val adapter = HorizontalListAdapter<Movie> { movie ->

        }

        _binding?.apply {
            layoutPopularMovies.tvTitle.text = getString(R.string.popular_movies)
            layoutPopularMovies.rvList.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            layoutPopularMovies.rvList.adapter = adapter

            adapter.submitList(results)

            if (results == null) {
                layoutPopularMoviesState.setState(MultiStateLayout.State.EMPTY)
            } else {
                layoutPopularMoviesState.setState(MultiStateLayout.State.CONTENT)
            }
        }
    }

    private fun setUpPopularTvSeries() {
        viewModel.getPopularTvSeries()
            .observe(viewLifecycleOwner) { response ->
                when (response?.status) {
                    StatefulData.Status.SUCCESS -> {
                        populatePopularTvShows(response.data?.results)
                    }
                    StatefulData.Status.ERROR -> {
                        _binding?.layoutPopularTvShowsState?.setState(MultiStateLayout.State.EMPTY)
                    }
                    StatefulData.Status.LOADING -> {
                        _binding?.layoutPopularTvShowsState?.setState(MultiStateLayout.State.LOADING)
                    }
                    null -> {
                        _binding?.layoutPopularTvShowsState?.setState(MultiStateLayout.State.EMPTY)
                    }
                }
            }
    }

    private fun populatePopularTvShows(results: List<TvSeries>?) {
        val adapter = HorizontalListAdapter<TvSeries> { tvShow ->

        }

        _binding?.apply {
            adapter.submitList(results)
            layoutPopularTvShows.tvTitle.text = getString(R.string.popular_tv_series)
            layoutPopularTvShows.rvList.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            layoutPopularTvShows.rvList.adapter = adapter

            if (results == null) {
                layoutPopularTvShowsState.setState(MultiStateLayout.State.EMPTY)
            } else {
                layoutPopularTvShowsState.setState(MultiStateLayout.State.CONTENT)
            }
        }
    }

    private fun setUpTrending() {
        viewModel.getTrendingContent()
            .observe(viewLifecycleOwner) { response ->
                when (response?.status) {
                    StatefulData.Status.SUCCESS -> {
                        populateTrendingContent(response.data?.results)
                    }
                    StatefulData.Status.ERROR -> {
                        _binding?.layoutTrendingContentState?.setState(MultiStateLayout.State.EMPTY)
                    }
                    StatefulData.Status.LOADING -> {
                        _binding?.layoutTrendingContentState?.setState(MultiStateLayout.State.LOADING)
                    }
                    null -> {
                        _binding?.layoutTrendingContentState?.setState(MultiStateLayout.State.EMPTY)
                    }
                }
            }
    }

    private fun populateTrendingContent(results: List<Trending>?) {
        val adapter = HorizontalListAdapter<Trending> { tvShow ->

        }

        _binding?.apply {
            adapter.submitList(results)
            layoutTrendingContent.tvTitle.text = getString(R.string.trending)
            layoutTrendingContent.rvList.layoutManager =
                GridLayoutManager(requireContext(),3)
            layoutTrendingContent.rvList.adapter = adapter

            if (results == null) {
                layoutTrendingContentState.setState(MultiStateLayout.State.EMPTY)
            } else {
                layoutTrendingContentState.setState(MultiStateLayout.State.CONTENT)
            }
        }
    }
}