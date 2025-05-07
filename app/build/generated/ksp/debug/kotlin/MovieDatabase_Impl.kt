import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.example.movieapplication.MovieDao
import com.example.movieapplication.MovieDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class MovieDatabase_Impl : MovieDatabase() {
  private val _movieDao: Lazy<MovieDao> = lazy {
    MovieDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "10f0293c1f889bd18bc0f14da944f112", "354e7bade3070be60cd77dc4a3c01d3f") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `Movie` (`title` TEXT NOT NULL, `year` TEXT NOT NULL, `rating` TEXT NOT NULL, `released` TEXT NOT NULL, `runtime` TEXT NOT NULL, `genre` TEXT NOT NULL, `director` TEXT NOT NULL, `writer` TEXT NOT NULL, `actors` TEXT NOT NULL, `plot` TEXT NOT NULL, PRIMARY KEY(`title`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '10f0293c1f889bd18bc0f14da944f112')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `Movie`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsMovie: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMovie.put("title", TableInfo.Column("title", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("year", TableInfo.Column("year", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("rating", TableInfo.Column("rating", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("released", TableInfo.Column("released", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("runtime", TableInfo.Column("runtime", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("genre", TableInfo.Column("genre", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("director", TableInfo.Column("director", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("writer", TableInfo.Column("writer", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("actors", TableInfo.Column("actors", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMovie.put("plot", TableInfo.Column("plot", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMovie: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesMovie: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMovie: TableInfo = TableInfo("Movie", _columnsMovie, _foreignKeysMovie,
            _indicesMovie)
        val _existingMovie: TableInfo = read(connection, "Movie")
        if (!_infoMovie.equals(_existingMovie)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |Movie(com.example.movieapplication.Movie).
              | Expected:
              |""".trimMargin() + _infoMovie + """
              |
              | Found:
              |""".trimMargin() + _existingMovie)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "Movie")
  }

  public override fun clearAllTables() {
    super.performClear(false, "Movie")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(MovieDao::class, MovieDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun movieDao(): MovieDao = _movieDao.value
}
