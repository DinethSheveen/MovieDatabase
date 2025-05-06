import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapplication.Movie
import com.example.movieapplication.MovieDao

@Database(entities = [Movie::class],version=1)
abstract class MovieDatabase:RoomDatabase(){
    abstract fun movieDao():MovieDao
}