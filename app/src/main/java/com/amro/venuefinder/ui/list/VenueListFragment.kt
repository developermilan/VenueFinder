package com.amro.venuefinder.ui.list

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amro.venuefinder.MainActivity
import com.amro.venuefinder.R
import com.amro.venuefinder.databinding.FragmentVenueListListBinding
import com.amro.venuefinder.network.NetworkAvailability
import com.amro.venuefinder.repository.VenueLocalDataSource
import com.amro.venuefinder.repository.VenueRemoteDataSource
import com.amro.venuefinder.repository.VenueRepository
import com.amro.venuefinder.ui.ViewModelProviderFactory
import com.amro.venuefinder.ui.detail.VenueDetailFragment
import com.google.android.material.snackbar.Snackbar

class VenueListFragment : Fragment() {

    private val vidKey = "VID"

    private lateinit var binding: FragmentVenueListListBinding
    private lateinit var venueListViewModel: VenueListViewModel
    private lateinit var venueAdapter: VenueItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val factory =
            ViewModelProviderFactory(
                VenueRepository(
                    VenueRemoteDataSource.getInstance(),
                    VenueLocalDataSource.getInstance(context?.applicationContext as Application)
                )
            )
        venueListViewModel = ViewModelProvider(this, factory).get(VenueListViewModel::class.java)

        updateUiOnDataChange()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_venue_list_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setActionBarTitle(getString(R.string.venue_list_title))
        (activity as MainActivity).disableBackButton()

        binding.apply {
            data = venueListViewModel
            lifecycleOwner = this@VenueListFragment
        }

        // Set the adapter
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = VenueItemRecyclerViewAdapter(venueListViewModel)
            venueAdapter = adapter as VenueItemRecyclerViewAdapter
            setHasFixedSize(true)
        }

        loadFromDatabaseIfInternetIsNotAvailable()
    }

    private fun loadFromDatabaseIfInternetIsNotAvailable() {
        if (!NetworkAvailability.isConnectedToInternet(requireContext())) {
            venueListViewModel.search(getString(R.string.any), false)
            Snackbar.make(
                binding.list,
                getString(R.string.internet_error),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun updateUiOnDataChange() {

        with(venueListViewModel) {

            venues.observe(this@VenueListFragment, {
                venueAdapter.updateData(venues.value ?: emptyList())
            })

            isLoading.observe(this@VenueListFragment, {
                binding.defaultText.visibility = View.GONE
            })

            isError.observe(this@VenueListFragment, {
                val message = it.message ?: getString(R.string.unknown_error)
                Snackbar.make(binding.list, message, Snackbar.LENGTH_LONG).show()
                loadFromDatabaseIfInternetIsNotAvailable()
            })

            clickedItemPosition.observe(this@VenueListFragment, {
                val bundle = Bundle()
                bundle.putString(vidKey, venues.value?.get(it)?.id)
                val venueDetailFragment = VenueDetailFragment()
                venueDetailFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction().replace(
                    R.id.container_view, venueDetailFragment
                ).addToBackStack(getString(R.string.fragment_tag)).commit()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        venueListViewModel.initialise(NetworkAvailability.isConnectedToInternet(requireContext()))
    }

    override fun onDestroyView() {
        binding.list.adapter = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val item = menu.findItem(R.id.menu_toolbar_search)
        val view = item.actionView as SearchView
        view.queryHint = getString(R.string.search_hint)

        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                venueListViewModel.search(
                    query,
                    NetworkAvailability.isConnectedToInternet(requireContext())
                )
                view.apply { if (!isIconified) isIconified = true }
                item.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
}