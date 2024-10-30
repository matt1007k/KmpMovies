package com.maxdev.kmpmovies.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.maxdev.kmpmovies.data.Movie
import com.maxdev.kmpmovies.ui.common.LoadingIndicator
import com.maxdev.kmpmovies.ui.screens.Screen
import me.sample.library.resources.Res
import me.sample.library.resources.back
import me.sample.library.resources.genres
import me.sample.library.resources.origin_language
import me.sample.library.resources.origin_title
import me.sample.library.resources.popularity
import me.sample.library.resources.release_date
import me.sample.library.resources.vote_average
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {
    val state = vm.state
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { state.movie?.let { Text(it.title) } },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                    }
                )
                scrollBehavior = scrollBehavior
            }
        ) { padding ->
            LoadingIndicator(state.loading, modifier = Modifier.padding(padding))

            state.movie?.let { movie ->
                DetailMovie(
                    movie = movie,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
private fun DetailMovie(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdrop ?: movie.poster,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )
        Text(
            text = movie.overview,
            modifier = Modifier.padding(16.dp),
        )
        Text(
            text = buildAnnotatedString {
                property(stringResource(Res.string.origin_language), movie.originalLanguage)
                property(stringResource(Res.string.origin_title), movie.originalTitle)
                property(stringResource(Res.string.release_date), movie.releaseDate)
                property(stringResource(Res.string.popularity), movie.popularity.toString())
                property(stringResource(Res.string.vote_average), movie.voteAverage.toString(), end = true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

private fun AnnotatedString.Builder.property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }
}