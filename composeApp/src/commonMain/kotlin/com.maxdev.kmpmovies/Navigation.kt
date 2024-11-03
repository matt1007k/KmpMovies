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
import com.maxdev.kmpmovies.data.database.MoviesDao
import com.maxdev.kmpmovies.ui.screens.detail.DetailScreen
import com.maxdev.kmpmovies.ui.screens.detail.DetailViewModel
import com.maxdev.kmpmovies.ui.screens.home.HomeScreen
import com.maxdev.kmpmovies.ui.screens.home.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.http.parametersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.sample.library.resources.Res
import me.sample.library.resources.api_key
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

@OptIn(KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(
                onMovieClick = { movie ->
                    navController.navigate(
                        "details/${movie.id}"
                    )
                }
            )
        }
        composable(
            route = "details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = checkNotNull(backStackEntry.arguments?.getInt("movieId"))
            DetailScreen(
                vm = koinViewModel(parameters = { parametersOf(movieId) }),
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}

@Composable
private fun rememberMoviesRepository(
    moviesDao: MoviesDao,
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
                parameters.append("api_key", BuildConfig.API_KEY)
            }
        }
    }
    MoviesRepository(MoviesService(client), moviesDao)
}