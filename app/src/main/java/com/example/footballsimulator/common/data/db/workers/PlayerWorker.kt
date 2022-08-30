package com.example.footballsimulator.common.data.db.workers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayerWorker(
    @Json(name = "identifier") val identifier: Long,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "team") val team: String,
    @Json(name = "position") val position: String,
    @Json(name = "image") val image: String
)
