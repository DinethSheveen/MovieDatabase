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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random
import kotlin.random.nextInt

class WebExtractActivity : ComponentActivity() {

    lateinit var db : MovieDatabase
    lateinit var movieDao : MovieDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(this,MovieDatabase::class.java,"MovieDatabase").build()
        movieDao = db.movieDao()

        setContent {
            GUI()
        }
    }
    @Composable
    fun GUI(){
        var context = LocalContext.current
        var results by rememberSaveable { mutableStateOf("") }
        var keyword by rememberSaveable{ mutableStateOf("") }
        var coroutineScope = rememberCoroutineScope()

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Web Extract", fontSize = 26.sp, color = Color.Cyan, textDecoration = TextDecoration.Underline,modifier = Modifier.padding(top = 50.dp), fontWeight = FontWeight.Bold)
        }
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(keyword, onValueChange = {keyword=it},label={Text("Enter the movie name")})

            Spacer(Modifier.height(10.dp))

            Button(onClick = {

                if(keyword.isBlank()){
                    Toast.makeText(context,"Fill in the field please",Toast.LENGTH_SHORT).show()
                }
                else {
                    coroutineScope.launch {
                        results = ""
                        results += extractFromWeb(keyword)
                    }
                }
            },
                Modifier.border(2.dp,Color.Cyan, RoundedCornerShape(30)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Cyan)
            ){
                Text("Extract", fontSize = 20.sp)
            }
            Column(
                Modifier.height(500.dp)
            ){
                Text(results,Modifier.padding(20.dp,20.dp).verticalScroll(rememberScrollState()), color = Color.White, fontSize = 20.sp,)
            }
        }
    }
}


suspend fun extractFromWeb(keyword: String): String {
    //CREATING THE URL
    val url = URL("https://www.omdbapi.com/?s=${keyword}&apikey=7c669c55")

    //ESTABLISHING THE HTTP CONNECTION
    val httpConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

    //INITIALIZING THE STRINGBUILDER WHICH HOLDS EVERY JSON DATA
    val jsonResults = StringBuilder()

    //GETTING THE RESULTS IN A NEW THREAD
    return withContext(Dispatchers.IO) {
        try {
            val bufferedReader = BufferedReader(InputStreamReader(httpConnection.inputStream))
            val response = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                response.append(line)
                line = bufferedReader.readLine()
            }

            val jsonObject = JSONObject(response.toString())

            if (jsonObject.has("Search")) {
                val searchResults = jsonObject.getJSONArray("Search")
                for (i in 0 until  searchResults.length()) {
                    val movie = searchResults.getJSONObject(i)
                    val title = movie.getString("Title")
                    val year = movie.getString("Year")

                    jsonResults.append("${i + 1}. $title  ($year)\n")
                }
            } else if (jsonObject.has("Error")) {
                jsonResults.append("${jsonObject.getString("Error")}")
            } else {
                jsonResults.append("No movies found.")
            }
        } catch (e: JSONException) {
            jsonResults.append("JSON parsing error.")
        } catch (e: Exception) {
            jsonResults.append("An error occurred: ${e.message}")
        }
        jsonResults.toString()
    }
}