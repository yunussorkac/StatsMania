package com.stats.mania

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stats.mania.screen.IndicatorsScreen
import com.stats.mania.ui.NavigationItem
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stats.mania.screen.CountriesScreen
import com.stats.mania.screen.CountryIndicatorDetailsScreen
import com.stats.mania.screen.CountryIndicatorsScreen
import com.stats.mania.screen.CountryTopicsScreen
import com.stats.mania.screen.IndicatorDetailsScreen
import com.stats.mania.screen.TopicsScreen
import com.stats.mania.ui.theme.StatsManiaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StatsManiaTheme {
                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route



    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            BottomNavigationBar(navController)

        },
        content = { padding ->
            Box(
                modifier = Modifier.padding(padding)
            ) {
                Navigation(navController = navController)
            }
        }
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Topics.route) {

        composable(NavigationItem.Topics.route) {
            TopicsScreen(navController)
        }

        composable(NavigationItem.Countries.route) {
            CountriesScreen(navController)
        }

        composable("countryTopics?countryId={countryId}&countryName={countryName}") { backStackEntry ->
            val countryId = backStackEntry.arguments?.getString("countryId") ?: ""
            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
            CountryTopicsScreen(navController, countryId, countryName)
        }


        composable("indicators?topicId={topicId}&topicValue={topicValue}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
            val topicValue = backStackEntry.arguments?.getString("topicValue") ?: ""
            IndicatorsScreen(navController, topicId, topicValue)
        }

        composable("indicatorDetails?indicatorId={indicatorId}&indicatorName={indicatorName}&sourceNote={sourceNote}") { backStackEntry ->
            val indicatorId = backStackEntry.arguments?.getString("indicatorId") ?: ""
            val indicatorName = backStackEntry.arguments?.getString("indicatorName") ?: ""
            val sourceNote = backStackEntry.arguments?.getString("sourceNote") ?: ""

            IndicatorDetailsScreen(navController, indicatorId, indicatorName, sourceNote)
        }

        composable("countryIndicators?topicId={topicId}&topicValue={topicValue}&countryId={countryId}") { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: ""
            val topicValue = backStackEntry.arguments?.getString("topicValue") ?: ""
            val countryId = backStackEntry.arguments?.getString("countryId") ?: ""

            CountryIndicatorsScreen(navController, topicId, topicValue,countryId)
        }

        composable("countryIndicatorDetails?indicatorId={indicatorId}&indicatorName={indicatorName}&countryId={countryId}&sourceNote={sourceNote}") { backStackEntry ->
            val indicatorId = backStackEntry.arguments?.getString("indicatorId") ?: ""
            val indicatorName = backStackEntry.arguments?.getString("indicatorName") ?: ""
            val countryId = backStackEntry.arguments?.getString("countryId") ?: ""
            val sourceNote = backStackEntry.arguments?.getString("sourceNote") ?: ""

            CountryIndicatorDetailsScreen(navController, indicatorId, indicatorName,countryId, sourceNote)
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Topics,
        NavigationItem.Countries
    )
    BottomNavigation(
        backgroundColor = Color(0xFF4F524F),
        contentColor = Color.White,


    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(
                    painterResource(id = item.icon),
                    contentDescription = item.title,
                    modifier = Modifier.size(22.dp),
                    tint = if (currentRoute == item.route) Color.Black else Color.White
                )},
                label = { Text(text = item.title,
                    color = if (currentRoute == item.route) Color.Black else Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    fontSize = 12.sp
                )},
                selectedContentColor = Color.Gray,
                unselectedContentColor = Color.Black,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true


                    }
                }
            )
        }
    }
}







