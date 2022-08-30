package com.example.footballsimulator.common.data.db.workers


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class testItem(
    @Json(name = "first_name")
    val firstName: String?,
    @Json(name = "identifier")
    val identifier: Int?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "last_name")
    val lastName: String?,
    @Json(name = "position")
    val position: String?,
    @Json(name = "team")
    val team: String?
)