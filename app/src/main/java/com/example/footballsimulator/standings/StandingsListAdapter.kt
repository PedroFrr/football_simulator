package com.example.footballsimulator.standings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footballsimulator.databinding.ItemTeamStandingBinding
import com.example.footballsimulator.databinding.ItemTeamStandingHeaderBinding
import com.example.footballsimulator.standings.domain.TeamStanding
import com.example.footballsimulator.standings.domain.TeamStandingDataItem

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_TEAM_STANDING = 1

class StandingsListAdapter : ListAdapter<TeamStandingDataItem, RecyclerView.ViewHolder>(FixtureListCallback()) {

    fun convertHeaderAndSubmit(teamStandings: List<TeamStanding>) {
        val teamStandingItems = listOf(TeamStandingDataItem.Header) + teamStandings.map { TeamStandingDataItem.TeamStandingItem(it) }
        submitList(teamStandingItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_TEAM_STANDING -> TeamStandingViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TeamStandingDataItem.TeamStandingItem -> ITEM_VIEW_TYPE_TEAM_STANDING
            is TeamStandingDataItem.Header -> ITEM_VIEW_TYPE_HEADER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TeamStandingViewHolder -> {
                val teamStandingItem = getItem(position) as TeamStandingDataItem.TeamStandingItem
                holder.bind(teamStandingItem.teamStanding)
            }
        }
    }

    class TeamStandingViewHolder(
        private val binding: ItemTeamStandingBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): TeamStandingViewHolder {
                return TeamStandingViewHolder(
                    ItemTeamStandingBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(teamStanding: TeamStanding) {
            binding.apply {
                tvPosition.text = adapterPosition.toString()
                tvTeam.text = teamStanding.teamName
                tvGoals.text = "${teamStanding.goalsScored} : ${teamStanding.goalsConceded}"
                tvPoints.text = teamStanding.points.toString()
            }
        }
    }

    class HeaderViewHolder(
        binding: ItemTeamStandingHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                return HeaderViewHolder(
                    ItemTeamStandingHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}

private class FixtureListCallback : DiffUtil.ItemCallback<TeamStandingDataItem>() {

    override fun areItemsTheSame(oldItem: TeamStandingDataItem, newItem: TeamStandingDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TeamStandingDataItem, newItem: TeamStandingDataItem): Boolean {
        return oldItem == newItem
    }
}
