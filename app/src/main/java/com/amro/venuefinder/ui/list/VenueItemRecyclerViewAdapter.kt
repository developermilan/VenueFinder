package com.amro.venuefinder.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.amro.venuefinder.R
import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.databinding.FragmentVenueListBinding

class VenueItemRecyclerViewAdapter(
    private val viewModel: VenueListViewModel
) : RecyclerView.Adapter<VenueItemRecyclerViewAdapter.ViewHolder>() {

    private val venues = ArrayList<Venue>()

    fun updateData(venueList: List<Venue>) {
        venues.apply {
            clear()
            addAll(venueList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<FragmentVenueListBinding>(
            LayoutInflater.from(parent.context), R.layout.fragment_venue_list, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(venues[position])
        holder.itemView.setOnClickListener { viewModel.itemClicked(position) }
    }

    override fun getItemCount(): Int = venues.size

    class ViewHolder(private val binding: FragmentVenueListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Venue) {
            binding.apply {
                this.data = item
                this.executePendingBindings()
            }
        }
    }
}