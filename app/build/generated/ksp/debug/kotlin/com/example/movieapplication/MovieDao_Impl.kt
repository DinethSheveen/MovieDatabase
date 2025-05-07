package com.example.movieapplication

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class MovieDao_Impl(
  __db: RoomDatabase,
) : MovieDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMovie: EntityInsertAdapter<Movie>

  private val __deleteAdapterOfMovie: EntityDeleteOrUpdateAdapter<Movie>
  init {
    this.__db = __db
    this.__insertAdapterOfMovie = object : EntityInsertAdapter<Movie>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `Movie` (`title`,`year`,`rating`,`released`,`runtime`,`genre`,`director`,`writer`,`actors`,`plot`) VALUES (?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Movie) {
        statement.bindText(1, entity.title)
        statement.bindText(2, entity.year)
        statement.bindText(3, entity.rating)
        statement.bindText(4, entity.released)
        statement.bindText(5, entity.runtime)
        statement.bindText(6, entity.genre)
        statement.bindText(7, entity.director)
        statement.bindText(8, entity.writer)
        statement.bindText(9, entity.actors)
        statement.bindText(10, entity.plot)
      }
    }
    this.__deleteAdapterOfMovie = object : EntityDeleteOrUpdateAdapter<Movie>() {
      protected override fun createQuery(): String = "DELETE FROM `Movie` WHERE `title` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Movie) {
        statement.bindText(1, entity.title)
      }
    }
  }

  public override suspend fun insertAll(vararg movies: Movie): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfMovie.insert(_connection, movies)
  }

  public override suspend fun delete(movie: Movie): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfMovie.handle(_connection, movie)
  }

  public override suspend fun getAllMovies(): List<Movie> {
    val _sql: String = "select * from Movie"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfReleased: Int = getColumnIndexOrThrow(_stmt, "released")
        val _columnIndexOfRuntime: Int = getColumnIndexOrThrow(_stmt, "runtime")
        val _columnIndexOfGenre: Int = getColumnIndexOrThrow(_stmt, "genre")
        val _columnIndexOfDirector: Int = getColumnIndexOrThrow(_stmt, "director")
        val _columnIndexOfWriter: Int = getColumnIndexOrThrow(_stmt, "writer")
        val _columnIndexOfActors: Int = getColumnIndexOrThrow(_stmt, "actors")
        val _columnIndexOfPlot: Int = getColumnIndexOrThrow(_stmt, "plot")
        val _result: MutableList<Movie> = mutableListOf()
        while (_stmt.step()) {
          val _item: Movie
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpYear: String
          _tmpYear = _stmt.getText(_columnIndexOfYear)
          val _tmpRating: String
          _tmpRating = _stmt.getText(_columnIndexOfRating)
          val _tmpReleased: String
          _tmpReleased = _stmt.getText(_columnIndexOfReleased)
          val _tmpRuntime: String
          _tmpRuntime = _stmt.getText(_columnIndexOfRuntime)
          val _tmpGenre: String
          _tmpGenre = _stmt.getText(_columnIndexOfGenre)
          val _tmpDirector: String
          _tmpDirector = _stmt.getText(_columnIndexOfDirector)
          val _tmpWriter: String
          _tmpWriter = _stmt.getText(_columnIndexOfWriter)
          val _tmpActors: String
          _tmpActors = _stmt.getText(_columnIndexOfActors)
          val _tmpPlot: String
          _tmpPlot = _stmt.getText(_columnIndexOfPlot)
          _item =
              Movie(_tmpTitle,_tmpYear,_tmpRating,_tmpReleased,_tmpRuntime,_tmpGenre,_tmpDirector,_tmpWriter,_tmpActors,_tmpPlot)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchByActor(actorName: String): List<Movie> {
    val _sql: String = "select * from Movie where actors LIKE ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, actorName)
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfYear: Int = getColumnIndexOrThrow(_stmt, "year")
        val _columnIndexOfRating: Int = getColumnIndexOrThrow(_stmt, "rating")
        val _columnIndexOfReleased: Int = getColumnIndexOrThrow(_stmt, "released")
        val _columnIndexOfRuntime: Int = getColumnIndexOrThrow(_stmt, "runtime")
        val _columnIndexOfGenre: Int = getColumnIndexOrThrow(_stmt, "genre")
        val _columnIndexOfDirector: Int = getColumnIndexOrThrow(_stmt, "director")
        val _columnIndexOfWriter: Int = getColumnIndexOrThrow(_stmt, "writer")
        val _columnIndexOfActors: Int = getColumnIndexOrThrow(_stmt, "actors")
        val _columnIndexOfPlot: Int = getColumnIndexOrThrow(_stmt, "plot")
        val _result: MutableList<Movie> = mutableListOf()
        while (_stmt.step()) {
          val _item: Movie
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpYear: String
          _tmpYear = _stmt.getText(_columnIndexOfYear)
          val _tmpRating: String
          _tmpRating = _stmt.getText(_columnIndexOfRating)
          val _tmpReleased: String
          _tmpReleased = _stmt.getText(_columnIndexOfReleased)
          val _tmpRuntime: String
          _tmpRuntime = _stmt.getText(_columnIndexOfRuntime)
          val _tmpGenre: String
          _tmpGenre = _stmt.getText(_columnIndexOfGenre)
          val _tmpDirector: String
          _tmpDirector = _stmt.getText(_columnIndexOfDirector)
          val _tmpWriter: String
          _tmpWriter = _stmt.getText(_columnIndexOfWriter)
          val _tmpActors: String
          _tmpActors = _stmt.getText(_columnIndexOfActors)
          val _tmpPlot: String
          _tmpPlot = _stmt.getText(_columnIndexOfPlot)
          _item =
              Movie(_tmpTitle,_tmpYear,_tmpRating,_tmpReleased,_tmpRuntime,_tmpGenre,_tmpDirector,_tmpWriter,_tmpActors,_tmpPlot)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "delete from Movie"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
