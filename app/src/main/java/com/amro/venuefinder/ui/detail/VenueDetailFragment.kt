package com.amro.venuefinder.ui.detail

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amro.venuefinder.MainActivity
import com.amro.venuefinder.R
import com.amro.venuefinder.databinding.VenueDetailFragmentBinding
import com.amro.venuefinder.network.NetworkAvailability
import com.amro.venuefinder.repository.VenueLocalDataSource
import com.amro.venuefinder.repository.VenueRemoteDataSource
import com.amro.venuefinder.repository.VenueRepository
import com.amro.venuefinder.ui.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar

class VenueDetailFragment : Fragment() {

    private val vid_key = "VID"
    private lateinit var binding: VenueDetailFragmentBinding
    private lateinit var viewModel: VenueDetailViewModel
    private lateinit var vid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ViewModelProviderFactory(
            VenueRepository(
                NetworkAvailability.isConnectedToInternet(
                    requireContext()
                ), VenueRemoteDataSource.getInstance(),
                VenueLocalDataSource.getInstance(context?.applicationContext as Application)
            )
        )
        viewModel = ViewModelProvider(this, factory).get(VenueDetailViewModel::class.java)
        vid = arguments?.getString(vid_key, "").toString()
        viewModel.fetchVenueDetails(vid)
    }

    private fun updateUiOnDataChange() {
        with(viewModel) {
            venue.observe(viewLifecycleOwner, Observer {
                binding.data = it
                binding.addressTv.text = it.location?.address?.joinToString(separator = " ")
                if (it.photos?.groups != null && it.photos.groups.isNotEmpty()
                    && it.photos.groups[0].items != null
                ) {
                    binding.imagePager.apply {
                        adapter = SlidingImageAdapter(it.photos)
                    }
                }
            })

            isError.observe(viewLifecycleOwner, Observer {
                val message = it.message ?: "Unknown error"
                Snackbar.make(binding.descriptionTv, message, Snackbar.LENGTH_LONG).show()
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.venue_detail_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).setActionBarTitle("Details")
        (activity as MainActivity).enableBackButton()

        updateUiOnDataChange()

        if (!NetworkAvailability.isConnectedToInternet(requireContext())) {
            Snackbar.make(
                binding.root,
                "Internet is not working. \nSo loading previous data",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}