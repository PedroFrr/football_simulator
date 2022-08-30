package com.example.footballsimulator.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footballsimulator.databinding.ItemPlayerBinding
import com.example.footballsimulator.players.domain.Player

class PlayersListAdapter() :
    ListAdapter<Player, RecyclerView.ViewHolder>(PlayerListCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayersViewHolder(
            ItemPlayerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val player = getItem(position)
        (holder as PlayersViewHolder).bind(player)
    }

    class PlayersViewHolder(
        private val binding: ItemPlayerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            player: Player,
        ) {
            binding.tvPlayerName.text = player.name
        }
    }
}

private class PlayerListCallback : DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.playerId == newItem.playerId
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}