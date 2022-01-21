package com.example.hostelmanagementsystem.Data

data class DistrictItems(
    val district_id: Int?,
    val district_name: String?
)

data class District(
    val districts: List<DistrictItems?>?,
    val ttl: Int?
)