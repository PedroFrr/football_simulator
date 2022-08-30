package com.example.footballsimulator.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footballsimulator.databinding.ItemTeamBinding
import com.example.footballsimulator.teams.domain.Team

class TeamsListAdapter(
    private val onTeamClicked: (String) -> Unit
) :
    ListAdapter<Team, RecyclerView.ViewHolder>(TeamListCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TeamsViewHolder(
            ItemTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val team = getItem(position)
        (holder as TeamsViewHolder).bind(team, onTeamClicked)
    }

    class TeamsViewHolder(
        private val binding: ItemTeamBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            team: Team,
            onTeamClicked: (String) -> Unit
        ) {
            binding.tvTeam.text = team.name

            binding.tvTeam.setOnClickListener {
                onTeamClicked(team.teamId)
            }
        }
    }
}

private class TeamListCallback : DiffUtil.ItemCallback<Team>() {

    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem.teamId == newItem.teamId
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
        return oldItem == newItem
    }
}
