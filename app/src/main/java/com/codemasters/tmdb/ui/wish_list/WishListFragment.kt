package com.codemasters.tmdb.ui.wish_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.codemasters.tmdb.R
import com.codemasters.tmdb.data.model.ContentDetails
import com.codemasters.tmdb.data.model.Movie
import com.codemasters.tmdb.databinding.FragmentWishListBinding
import com.codemasters.tmdb.ui.content_details.ContentViewModel
import com.codemasters.tmdb.ui.discover.DiscoverFragmentDirections
import com.codemasters.tmdb.ui.discover.HorizontalListAdapter
import com.codemasters.tmdb.ui.widgets.MultiStateLayout
import kotlinx.coroutines.flow.catch

class WishListFragment : Fragment(R.layout.fragment_wish_list) {
    private var _binding: FragmentWishListBinding? = null

    private val viewModel by viewModels<ContentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentWishListBinding.bind(view)

        setUpUI()
        observeWishList()
    }

    private fun setUpUI() {
        _binding?.apply {
            appBarLayout.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24)
            appBarLayout.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    private fun observeWishList() {
        viewModel.getWishList()
            .catch { exception ->
                exception.printStackTrace()
                _binding?.apply {
                    layoutContentState.setState(MultiStateLayout.State.EMPTY)
                }
            }
            .asLiveData().observe(viewLifecycleOwner) { list ->
                populateList(list)
            }
    }

    private fun populateList(list: List<ContentDetails>?) {
        val adapter = WishListAdapter { content ->
            val direction =
                WishListFragmentDirections.actionWishListFragmentToContentDetailsFragment(
                    content.content_type,
                    content.id
                )
            findNavController().navigate(direction)
        }

        adapter.submitList(list)
        _binding?.apply {
            rvContents.adapter = adapter

            if (list.isNullOrEmpty()) {
                layoutContentState.setState(MultiStateLayout.State.EMPTY)
            } else {
                layoutContentState.setState(MultiStateLayout.State.CONTENT)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}