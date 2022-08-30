package com.example.footballsimulator.fixtures.domain

sealed class FixtureDataItem {
    abstract val id: String

    data class FixtureItem(val fixture: Fixture) : FixtureDataItem() {
        override val id = fixture.fixtureId
    }

    data class RoundItem(val round: String) : FixtureDataItem() {
        override val id = round
    }
}
