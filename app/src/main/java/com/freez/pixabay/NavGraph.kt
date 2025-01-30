package com.freez.pixabay

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.freez.pixabay.core.util.Screen
import com.freez.pixabay.presentation.video.videoDetail.VideoDetailScreen
import com.freez.pixabay.presentation.video.videoList.VideoListScreen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { PixabayAppTopBar(scrollBehavior) },
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.VideoListScreen.route,
        ) {
            composable(route = Screen.VideoListScreen.route) {
                VideoListScreen(navController = navController)
            }
            composable(
                route = Screen.VideoDetailScreen.route,
                arguments = listOf(navArgument("videoId") { type = NavType.LongType }),
            ) { backStackEntry ->
                val videoId = backStackEntry.arguments?.getLong("videoId")
                videoId?.let {
                    VideoDetailScreen(videoId = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixabayAppTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    MediumTopAppBar(
        title = { Text(text = "Pixabay Videos", fontSize = 24.sp) },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
    )
}
