package com.codemasters.tmdb.ui.content_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.codemasters.tmdb.R
import com.codemasters.tmdb.data.model.ContentDetails
import com.codemasters.tmdb.data.model.ContentType
import com.codemasters.tmdb.data.model.StatefulData
import com.codemasters.tmdb.databinding.FragmentContentDetailsBinding
import com.codemasters.tmdb.databinding.FragmentDiscoverBinding
import com.codemasters.tmdb.ui.discover.DiscoverViewModel
import com.codemasters.tmdb.ui.widgets.MultiStateLayout
import com.codemasters.tmdb.utils.toBackDropImageUrl
import com.codemasters.tmdb.utils.toPosterImageUrl

class ContentDetailsFragment : Fragment(R.layout.fragment_content_details) {
    private var _binding: FragmentContentDetailsBinding? = null

    private val args by navArgs<ContentDetailsFragmentArgs>()
    private val viewModel by viewModels<ContentDetailsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentContentDetailsBinding.bind(view)

        setUpUI()
        getDetails(args.type, args.contentId)
    }

    private fun getDetails(type: Int, contentId: Int) {
        val contentType = if (type == 0) ContentType.movie else ContentType.tv

        viewModel.getContentDetails(contentType, contentId)
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
            _binding?.contentDetailsState?.setState(MultiStateLayout.State.EMPTY)
        } else {
            _binding?.contentDetailsState?.setState(MultiStateLayout.State.CONTENT)

            _binding?.apply {
                ivBackDrop.load(data.backDrop?.toBackDropImageUrl())
                ivPoster.load(data.poster?.toPosterImageUrl()) {
                    error(R.mipmap.ic_launcher)
                    fallback(R.mipmap.ic_launcher)
                    placeholder(R.mipmap.ic_launcher)
                }

                val title = data.name ?: data.title

                appBarLayout.toolbar.title = title
                tvTitle.text = title
                tvOverView.text = data.overview
                tvRating.text = data.vote_average.toString()
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