package com.haneetarya.mychat.data

data class MessageData(
    val message: String,
    val user: String,
    val self: Boolean = false
)
