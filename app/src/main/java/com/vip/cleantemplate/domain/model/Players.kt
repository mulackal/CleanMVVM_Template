package com.vip.cleantemplate.domain.model

data class Players(
    val data: List<Player>,
    val playerMeta: PlayerMeta
)


data class Player(
    val first_name: String,
    val id: Int,
    val last_name: String,
    val position: String,
    val team: Team,
)

data class Team(
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val id: Int,
    val name: String
)

data class PlayerMeta(
    val current_page: Int,
    val next_page: Int?,
    val per_page: Int,
    val total_count: Int,
    val total_pages: Int
)