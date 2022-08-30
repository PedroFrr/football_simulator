package com.example.footballsimulator.fixtures

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.footballsimulator.common.util.MM_DD
import com.example.footballsimulator.common.util.YYYY_MM_DD
import com.example.footballsimulator.common.util.formatToPattern
import com.example.footballsimulator.common.util.hide
import com.example.footballsimulator.databinding.ItemFixtureBinding
import com.example.footballsimulator.databinding.ItemRoundBinding
import com.example.footballsimulator.fixtures.domain.Fixture
import com.example.footballsimulator.fixtures.domain.FixtureDataItem

private const val ITEM_VIEW_TYPE_ROUND = 0
private const val ITEM_VIEW_TYPE_FIXTURE = 1

class FixturesListAdapter : ListAdapter<FixtureDataItem, RecyclerView.ViewHolder>(FixtureListCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ROUND -> RoundViewHolder.from(parent)
            ITEM_VIEW_TYPE_FIXTURE -> FixtureViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FixtureDataItem.FixtureItem -> ITEM_VIEW_TYPE_FIXTURE
            is FixtureDataItem.RoundItem -> ITEM_VIEW_TYPE_ROUND
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FixtureViewHolder -> {
                val fixtureItem = getItem(position) as FixtureDataItem.FixtureItem
                holder.bind(fixtureItem.fixture)
            }
            is RoundViewHolder -> {
                val roundItem = getItem(position) as FixtureDataItem.RoundItem
                holder.bind(roundItem)
            }
        }
    }

    class FixtureViewHolder(
        private val binding: ItemFixtureBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): FixtureViewHolder {
                return FixtureViewHolder(
                    ItemFixtureBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(fixture: Fixture) {
            binding.apply {
                tvHomeTeamName.text = fixture.homeTeam.name

                // set winning team name to bold
                val homeTeamTypeFace = if ((fixture.homeTeamScore ?: 0) > (fixture.awayTeamScore ?: 0)) Typeface.BOLD else Typeface.NORMAL
                val awayTeamTypeFace = if ((fixture.homeTeamScore ?: 0) < (fixture.awayTeamScore ?: 0)) Typeface.BOLD else Typeface.NORMAL

                tvHomeTeamName.setTypeface(tvHomeTeamName.typeface, homeTeamTypeFace)
                tvHomeTeamScore.setTypeface(tvHomeTeamScore.typeface, homeTeamTypeFace)

                tvAwayTeamName.setTypeface(tvAwayTeamName.typeface, awayTeamTypeFace)
                tvAwayTeamScore.setTypeface(tvAwayTeamScore.typeface, awayTeamTypeFace)

                fixture.homeTeamScore?.let { tvHomeTeamScore.text = it.toString() } ?: tvHomeTeamScore.hide()
                tvAwayTeamName.text = fixture.awayTeam.name
                fixture.awayTeamScore?.let { tvAwayTeamScore.text = it.toString() } ?: tvAwayTeamScore.hide()
                tvFixtureDate.text = fixture.date.formatToPattern(inputFormat = YYYY_MM_DD, outputFormat = MM_DD)
            }
        }
    }

    class RoundViewHolder(
        private val binding: ItemRoundBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): RoundViewHolder {
                return RoundViewHolder(
                    ItemRoundBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(roundItem: FixtureDataItem.RoundItem) {
            binding.tvRound.text = roundItem.round
        }
    }
}

private class FixtureListCallback : DiffUtil.ItemCallback<FixtureDataItem>() {

    override fun areItemsTheSame(oldItem: FixtureDataItem, newItem: FixtureDataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FixtureDataItem, newItem: FixtureDataItem): Boolean {
        return oldItem == newItem
    }
}
