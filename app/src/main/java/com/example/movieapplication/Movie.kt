package com.example.movieapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey val title:String,
    val year : String,
    val rating: String,
    val released : String,
    val runtime : String,
    val genre : String,
    val director : String,
    val writer:String,
    val actors : String,
    val plot : String
)