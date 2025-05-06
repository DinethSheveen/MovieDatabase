package com.example.movieapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("select * from Movie")
    suspend fun getAllMovies():List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg movies:Movie)

    @Delete
    suspend fun delete(movie : Movie)

    @Query("delete from Movie")
    suspend fun deleteAll()

    @Query("select * from Movie where actors LIKE :actorName")
    suspend fun searchByActor(actorName:String):List<Movie>

}