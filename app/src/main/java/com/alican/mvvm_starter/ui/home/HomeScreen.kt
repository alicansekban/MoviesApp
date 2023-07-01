package com.alican.mvvm_starter.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success


@Composable
fun HomeScreen(
    openList: () -> Unit,
    openDetail: (Int) -> Unit,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {


    val popularMovies by viewModel.popularMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val topRatedMovies by viewModel.topRatedMovies.collectAsState()
    val upComingMovies by viewModel.upComingMovies.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = { HomeScreenToolbar() }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            when (popularMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_popular_movies),
                        movies = (popularMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList()},
                        onItemClick = { openDetail(it) }
                    )
                }
            }
            when (upComingMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_popular_movies),
                        movies = (upComingMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList()},
                        onItemClick = { openDetail(it) }
                    )
                }
            }
            when (nowPlayingMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_popular_movies),
                        movies = (nowPlayingMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList()},
                        onItemClick = { openDetail(it) }
                    )
                }
            }
            when (topRatedMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_popular_movies),
                        movies = (topRatedMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList()},
                        onItemClick = { openDetail(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeSection(
    title: String,
    movies: List<MovieUIModel>,
    onSeeAllClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            style = TextStyle.Default,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(
                items = movies,
                key = { _, movie ->
                    movie.id
                }
            ) { index, value ->
                HomeMovieItem(movie = value,onItemClick = onItemClick)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSeeAllClick,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.txt_see_all),
                fontFamily = FontFamily(Font(R.font.sfprodisplay_semi_bold)),
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun HomeMovieItem(movie: MovieUIModel, onItemClick: (Int) -> Unit) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(
            data = movie.poster_path // Movie nesnesinde uygun bir poster URL'si olduğunu varsayalım
        ).apply(block = fun ImageRequest.Builder.() {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
        }).build()
    )


    Column(modifier = Modifier.width(120.dp)) {
        Image(
            painter = rememberAsyncImagePainter(painter),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, 180.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { onItemClick(movie.id) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = TextStyle.Default,
            fontSize = 14.sp,
            color = Color.White,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
fun HomeScreenToolbar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.get_home_title)) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = MaterialTheme.colors.onPrimary
    )
}

