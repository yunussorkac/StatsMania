package com.stats.mania.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.stats.mania.R
import com.stats.mania.adapter.TopicAdapter
import com.stats.mania.viewmodel.TopicViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopicsScreen(navController : NavHostController) {

    val context = LocalContext.current
    val topicViewModel = koinViewModel<TopicViewModel>()

    val topicsList = topicViewModel.topics.value


    LaunchedEffect(Unit) {
        topicViewModel.fetchTopics()
    }



    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column (modifier = Modifier.fillMaxSize()
                ) {

                Text(text = "Topics (${topicsList.size})", fontSize = 18.sp, color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.poppins_bold))
                    , modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp))


                LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
                    items(topicsList) { topic ->
                        TopicAdapter(topic = topic, onClick = {
                            navController.navigate("indicators?topicId=${topic.id}&topicValue=${topic.value}"){
                            }
                        })
                    }
                }

            }
        }



    }



}