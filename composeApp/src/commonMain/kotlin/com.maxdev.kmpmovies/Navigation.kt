package com.maxdev.kmpmovies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.maxdev.kmpmovies.data.MoviesRepository
import com.maxdev.kmpmovies.data.MoviesService
import com.maxdev.kmpmovies.ui.screens.detail.DetailScreen
import com.maxdev.kmpmovies.ui.screens.detail.DetailViewModel
import com.maxdev.kmpmovies.ui.screens.home.HomeScreen
import com.maxdev.kmpmovies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.sample.library.resources.Res
import me.sample.library.resources.api_key
import org.jetbrains.compose.resources.stringResource

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val repository = rememberMoviesRepository()

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(
                        "details/${movie.id}"
                    )
                },
                vm = viewModel {
                    HomeViewModel(repository)
                }
            )
        }
        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = viewModel { DetailViewModel(id = movieId, repository = repository) },
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    apiKey: String = stringResource(Res.string.api_key)
): MoviesRepository = remember {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(DefaultRequest) {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                parameters.append("api_key", apiKey)
            }
        }
    }
    MoviesRepository(MoviesService(client))
}