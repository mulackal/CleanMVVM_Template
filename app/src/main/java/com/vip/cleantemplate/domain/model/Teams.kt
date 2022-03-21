package com.vip.cleantemplate.domain.model

data class Teams(
    val `data`: List<Data>,
    val teamMeta: TeamMeta
)

data class Data(
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val full_name: String,
    val id: Int,
    val name: String
)

data class TeamMeta(
    val current_page: Int,
    val next_page: Any,
    val per_page: Int,
    val total_count: Int,
    val total_pages: Int
)