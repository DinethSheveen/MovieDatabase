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
import androidx.compose.ui.graphics.Color
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

class SearchActorActivity : ComponentActivity() {
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

        var userKeyword by rememberSaveable { mutableStateOf("") }
        val coroutineScope  = rememberCoroutineScope()
        val context = LocalContext.current
        var actorInfo by rememberSaveable { mutableStateOf("") }

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Actor Search", fontSize = 26.sp, color = Color.Cyan, textDecoration = TextDecoration.Underline,modifier = Modifier.padding(top = 50.dp), fontWeight = FontWeight.Bold)
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(userKeyword, onValueChange = {userKeyword=it}, label = {Text("Enter Actor Name Here...")})
            Spacer(Modifier.height(20.dp))
            Row {
                Button(onClick = {
                    if(userKeyword.isBlank()){
                        Toast.makeText(context,"Fill Out The Missing Fields", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        coroutineScope.launch{
                            var actors = movieDao.searchByActor("%$userKeyword%")

                            actorInfo = ""

                            if(actors.isEmpty()){
                                actorInfo = "No movies found with actor '${userKeyword}'"
                            }
                            else {
                                for (actor in actors) {
                                    actorInfo += "Title : ${actor.title}\nYear : ${actor.year}\nRated : ${actor.rating}\nReleased : ${actor.released}\nRuntime : ${actor.runtime}\nGenre : ${actor.genre}\nDirector : ${actor.director}\nWriter : ${actor.writer}\nActor : ${actor.actors}\n\nPlot : ${actor.plot}\n\n\n"
                                }
                            }
                        }
                    }
                },
                    modifier = Modifier.border(2.dp, Color.Cyan, RoundedCornerShape(30)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)){
                    Text("Search Actor", fontSize = 20.sp)
                }
            }
            Column(
                Modifier.height(500.dp)
            ){
                Text(actorInfo, fontSize = 20.sp, modifier = Modifier.verticalScroll(rememberScrollState()).padding(20.dp,20.dp), color = Color.White)
            }
        }
    }

}
