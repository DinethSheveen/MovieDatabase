package com.example.movieapplication

import MovieDatabase
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {

    //DECLARING THE DATABASE AND THE DAO OBJECT
    lateinit var db:MovieDatabase
    lateinit var movieDao:MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //INITIALIZING THE DATABASE AND THE DAO OBJECT
        db = Room.databaseBuilder(this,MovieDatabase::class.java,"MovieDatabase").build()
        movieDao = db.movieDao()

        setContent {
            GUI()
        }
    }
    @Composable
    fun GUI(){

        var context = LocalContext.current
        var coroutineScope = rememberCoroutineScope()


        Column(
            Modifier.fillMaxSize().background(Color.DarkGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Welcome to the Movie Database", fontSize = 26.sp, color = Color.Cyan, textDecoration = TextDecoration.Underline,modifier = Modifier.padding(top = 50.dp), fontWeight = FontWeight.Bold)
            Column(Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                )
            {
                Button(onClick = {
                    coroutineScope.launch{
                        movieDao.insertAll(
                            Movie(title="The Shawshank Redemption", year = "1994",rating="R", released = "14 Oct 1994", runtime = "142 min", genre = "Drama", director = "Frank Darabont",writer="Stephen King, Frank Darabont", actors = "Tim Robbins, Morgan Freeman, Bob Gunton", plot = "Two imprisoned men bond over a number of years finding solace and eventual redemption through acs of common decency"),
                            Movie(title="Batman : The Dark Knight Returns, Part 1", year = "2012",rating="PG-13", released = "25 Sep 2012", runtime = "76 min", genre = "Animation, action, crime, drama, thriller", director = "Jay Oliva", writer="Bob Kane(character created by : Batman, Frank Miller (comic book), Klaus Jenson (comic book),  Bob Goodman", actors = "Peter Weller, Ariel Winter, David Shelby, Wade Williams", plot = "Batman has not been seen for ten years. A new breed of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back into the cape and cowl. But, does he still have what it takes to fight crime in a new era?"),
                            Movie(title="The Lord Of The Rings : The Return Of The King", year = "2003",rating="PG-13", released = "17 Dec 2003", runtime = "201 min", genre = "Action, Adventure, Drama", director = "Peter Jackson", writer="J.R.R. Tolkien, Fran Walsh, Philippa Boyens", actors = "Elijah Wood, Viggo Mortensen, Ian McKellen", plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring"),
                            Movie(title="Inception", year = "2010", rating="PG-13", released = "16 Jul 2010", runtime = "148 min", genre = "Action, Adventure, Sci-Fic", director = "Christopher Nolan", writer="Christopher Nolan", actors = "Leoanardo DiCaprio, Joseph Gordon-Levitt, Elliot Page", plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.,  but his tragic past may doom the project and his team to disaster"),
                            Movie(title="The Matrix", year = "1999",rating="R", released = "31 Mar 1999", runtime = "136 min", genre = "Action, Sci-Fic", director = "Lana Wachowski, Lilly Wachowski", writer="Lana Wachowski, Lilly Wachowski", actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss", plot = "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence")
                        )
                    }
                    Toast.makeText(context,"Movies Added Successfully",Toast.LENGTH_SHORT).show()
                },
                    Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)
                ) {
                    Text("Add Movies", fontSize = 20.sp)
                }

                Spacer(Modifier.height(20.dp))

                Button(onClick = {
                    var searchActorActivity = Intent(context,SearchActorActivity::class.java)
                    context.startActivity(searchActorActivity)
                },
                    Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)
                ) {
                    Text("Search Actors", fontSize = 20.sp)
                }

                Spacer(Modifier.height(20.dp))

                Button(onClick = {
                    var searchMovieActivity = Intent(context,SearchMovieActivity::class.java)
                    context.startActivity(searchMovieActivity)
                },
                    Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)
                ) {
                    Text("Search for Movies", fontSize = 20.sp)
                }

                Spacer(Modifier.height(20.dp))

                Button(onClick={
                    var i = Intent(context,WebExtractActivity::class.java)
                    context.startActivity(i)
                },
                    Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)), colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)
                )
                {
                    Text("Extract from the Web Directly",fontSize=20.sp)
                }
            }
        }
    }
}
