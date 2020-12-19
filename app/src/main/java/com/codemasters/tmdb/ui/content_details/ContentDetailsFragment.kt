package com.codemasters.tmdb.ui.content_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.navArgs
import coil.load
import com.codemasters.tmdb.R
import com.codemasters.tmdb.data.model.ContentDetails
import com.codemasters.tmdb.data.model.MediaType
import com.codemasters.tmdb.data.model.StatefulData
import com.codemasters.tmdb.databinding.FragmentContentDetailsBinding
import com.codemasters.tmdb.ui.widgets.MultiStateLayout
import com.codemasters.tmdb.utils.toBackDropImageUrl
import com.codemasters.tmdb.utils.toPosterImageUrl
import kotlinx.coroutines.flow.catch

class ContentDetailsFragment : Fragment(R.layout.fragment_content_details) {
    private var _binding: FragmentContentDetailsBinding? = null

    private val args by navArgs<ContentDetailsFragmentArgs>()
    private val viewModel by viewModels<ContentViewModel>()
    private var isFavorite = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentContentDetailsBinding.bind(view)

        setUpUI()
        getDetails(args.type, args.contentId)
        observeWishList()
    }

    private fun observeWishList() {
        viewModel.getWishList()
            .catch { exception ->
                exception.printStackTrace()
            }
            .asLiveData().observe(viewLifecycleOwner) { list ->
                var foundInDb = false
                for (content in list) {
                    if (content.id == args.contentId) {
                        foundInDb = true
                        break
                    }
                }

                isFavorite = foundInDb

                _binding?.apply {
                    if (foundInDb) {
                        fab.text = getString(R.string.remove_from_wish_list)
                        fab.setIconResource(R.drawable.ic_favorite_filled_24)
                    } else {
                        fab.text = getString(R.string.add_to_wish_list)
                        fab.setIconResource(R.drawable.ic_favorite_border_24)
                    }
                }
            }
    }

    private fun getDetails(type: String, contentId: Int) {

        viewModel.getContentDetails(type, contentId)
            .observe(viewLifecycleOwner) { response ->
                when (response?.status) {
                    StatefulData.Status.SUCCESS -> {
                        populateDetails(response.data)
                    }
                    StatefulData.Status.ERROR -> {
                        _binding?.contentDetailsState?.setState(MultiStateLayout.State.EMPTY)
                    }
                    StatefulData.Status.LOADING -> {
                        _binding?.contentDetailsState?.setState(MultiStateLayout.State.LOADING)
                    }
                    null -> {
                        _binding?.contentDetailsState?.setState(MultiStateLayout.State.EMPTY)
                    }
                }
            }
    }

    private fun populateDetails(data: ContentDetails?) {
        if (data == null) {
            _binding?.apply {
                contentDetailsState.setState(MultiStateLayout.State.EMPTY)
                fab.hide()
            }

        } else {
            _binding?.contentDetailsState?.setState(MultiStateLayout.State.CONTENT)

            _binding?.apply {
                ivBackDrop.load(data.backDrop?.toBackDropImageUrl())
                ivPoster.load(data.poster?.toPosterImageUrl())

                val title = data.name ?: data.title

                appBarLayout.toolbar.title = title
                tvTitle.text = title
                tvOverView.text = data.overview
                tvRating.text = data.vote_average.toString()

                fab.show()

                fab.setOnClickListener {
                    if (isFavorite) viewModel.deleteContent(data)
                    else {
                        viewModel.insertContent(data.copy(content_type = args.type))
                    }
                }
            }
        }
    }

    private fun setUpUI() {
        _binding?.apply {
            appBarLayout.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_24)
            appBarLayout.toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}