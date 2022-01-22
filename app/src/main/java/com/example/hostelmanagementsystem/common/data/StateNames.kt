package com.example.hostelmanagementsystem.common.data

data class State(
    val states: List<StateDetails>,
    val ttl: Int
)

data class StateDetails(
    val state_id: String,
    val state_name: String
)

