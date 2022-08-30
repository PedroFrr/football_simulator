package com.example.footballsimulator.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footballsimulator.databinding.ItemTeamStandingBinding
import com.example.footballsimulator.standings.domain.TeamStanding

class StandingsListAdapter :
    ListAdapter<TeamStanding, RecyclerView.ViewHolder>(TeamListCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TeamsViewHolder(
            ItemTeamStandingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val teamStanding = getItem(position)
        (holder as TeamsViewHolder).bind(teamStanding)
    }

    class TeamsViewHolder(
        private val binding: ItemTeamStandingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(teamStanding: TeamStanding) {
            binding.tvTeamPosition.text = teamStanding.standingPosition.toString()
            binding.tvTeamName.text = teamStanding.team.name
        }
    }
}

private class TeamListCallback : DiffUtil.ItemCallback<TeamStanding>() {

    override fun areItemsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
        return oldItem.standingPosition == newItem.standingPosition
    }

    override fun areContentsTheSame(oldItem: TeamStanding, newItem: TeamStanding): Boolean {
        return oldItem == newItem
    }
}
