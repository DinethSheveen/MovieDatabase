package com.example.movieapplication

import MovieDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMovieActivity : ComponentActivity() {

    lateinit var db : MovieDatabase
    lateinit var movieDao: MovieDao

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
        var results by rememberSaveable { mutableStateOf("") }              //VARIABLE TO STORE THE RESULTS RETRIEVED FROM THE WEB SERVICE
        var userKeyword by rememberSaveable { mutableStateOf("") }          //VARIABLE TO STORE THE INPUT TEXT OF THE TEXTFIELD
        var coroutineScope = rememberCoroutineScope()               //COROUTINE VARIABLE TO ACCESS ALL THE MOVIEDAO
        var resultJson by rememberSaveable { mutableStateOf(mutableListOf("")) }

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Movie Search", fontSize = 26.sp, color = Color.Cyan, textDecoration = TextDecoration.Underline,modifier = Modifier.padding(top = 50.dp), fontWeight = FontWeight.Bold)
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(userKeyword, onValueChange = {userKeyword=it}, label = {Text("Search Movie here...")})
            Spacer(Modifier.height(20.dp))
            Row {
                Button(onClick = {
                    if(userKeyword.isBlank()){
                        Toast.makeText(context,"Fill in the required field",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        coroutineScope.launch {
                            results = retrieveMovies(userKeyword)
                        }
                    }
                },
                    modifier = Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)){
                    Text("Retrieve Movie", fontSize = 20.sp)
                }

                Spacer(Modifier.width(10.dp))

                Button(onClick = {
                    resultJson = results.split("\n") as MutableList<String>

                    //INSERTING THE RETRIEVED MOVIE TO THE DATABASE THROUGH A SPECIFIC COROUTINE
                    coroutineScope.launch {
                        movieDao.insertAll(
                            Movie(
                                title = resultJson[0],
                                year = resultJson[1],
                                rating = resultJson[2],
                                released = resultJson[3],
                                runtime = resultJson[4],
                                genre = resultJson[5],
                                director = resultJson[6],
                                writer = resultJson[7],
                                actors = resultJson[8],
                                plot = resultJson[9]
                            )
                        )
                    }
                    //A MESSAGE TO NOTIFY THAT THE MOVIE HAS BEEN ADDED TO THE DATABASE
                    Toast.makeText(context,"Movie has been successfully added to the database",Toast.LENGTH_SHORT).show()
                },
                    modifier=Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)){
                    Text("Save Movie", fontSize = 20.sp)
                }
            }
            Column(
                Modifier.height(500.dp)
            ){
                Text(results,modifier=Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 20.dp), color = Color.White, fontSize = 20.sp)
            }
        }
    }
}

suspend fun retrieveMovies(keyword:String):String{
    //CREATING THE URL
    val url = URL("https://www.omdbapi.com/?t=${keyword}&apikey=7c669c55")

    //ESTABLISHING THE HTTP CONNECTION
    val httpConnection : HttpURLConnection = url.openConnection() as HttpURLConnection

    //INITIALIZING THE STRINGBUILDER WHICH HOLDS EVERY JSON DATA
    val jsonResponse = StringBuilder()

    //GETTING THE RESULTS IN A NEW THREAD
    withContext(Dispatchers.IO){
        var bufferReader = BufferedReader(InputStreamReader(httpConnection.inputStream))
        var line :String? = bufferReader.readLine()
        while(line!=null){
            jsonResponse.append(line+"\n")
            line = bufferReader.readLine()
        }
    }

    val movies = JSONParse(jsonResponse)
    return movies
}

fun JSONParse(jsonResponse:StringBuilder):String {
    var movies = StringBuilder()

    //CREATING THE JSON OBJECT (THE WHOLE DOCUMENT)
    val json = JSONObject(jsonResponse.toString())

    try {
        val movieTitle = json["Title"]
        val year = json["Year"]
        val rating = json["Rated"]
        val released = json["Released"]
        val runtime = json["Runtime"]
        val genre = json["Genre"]
        val director = json["Director"]
        val writer = json["Writer"]
        val actors = json["Actors"]
        val plot = json["Plot"]

        movies.append(
            "Title : $movieTitle\nYear : ${year}\nRated : ${rating}\nReleased : ${released}\nRuntime : ${runtime}\nGenre : ${genre}\nDirector : ${director}\nWriter : ${writer}\nActors : ${actors}\n\nPlot : ${plot}"
        )
    }
    catch(e:JSONException){
        movies.append("No movie found...")
    }
    return movies.toString()
}