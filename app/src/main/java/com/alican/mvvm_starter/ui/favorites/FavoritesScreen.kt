package com.alican.mvvm_starter.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alican.mvvm_starter.customViews.LoadingView
import com.alican.mvvm_starter.customViews.TopBar
import com.alican.mvvm_starter.data.local.model.FavoritesEntity
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.ui.theme.Black
import com.alican.mvvm_starter.ui.theme.White

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
) {

    val movies by viewModel.favoriteMovies.collectAsStateWithLifecycle()
    val removedState by viewModel.removeFavoriteMovie.collectAsStateWithLifecycle()


    val searchQuery: MutableState<String> = remember { mutableStateOf("") }


    LaunchedEffect(key1 = searchQuery.value) {
        if (searchQuery.value.length > 3) {
            viewModel.getFavorites(searchQuery.value)
        } else {
            viewModel.getFavorites("")
        }
    }
    when (removedState) {
        is Error -> {}
        is Loading -> {LoadingView()}
        is Success -> {
                viewModel.getFavorites()
        }
    }
    when (movies) {
        is Error ->  {}
        is Loading -> { LoadingView()}

        is Success -> {
            stateLessFavorites(
                movies = (movies as Success<List<FavoritesEntity>>).response,
                openDetail = { openDetail(it) },
                popBackStack = { popBackStack(it) },
                searchQuery = searchQuery.value,
                onSearchQueryChange = { newValue ->
                    searchQuery.value = newValue
                },
                removeFavoriteClicked =  {
                    viewModel.removeFavoriteMovie(it)
                }
            )
        }
    }

}

@Composable
fun stateLessFavorites(
    movies: List<FavoritesEntity>,
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    removeFavoriteClicked : (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Favorite Movies",
                showBackButton = true,
                onBackClick = { popBackStack("-1") },
                showFavoriteButton = false,
                onFavoriteClick = { })
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 8.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery, onValueChange = {
                        onSearchQueryChange(it)

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = CircleShape,
                    placeholder = {
                        Text(text = "Search...")
                    },
                    maxLines = 1,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = rememberLazyGridState()
                ) {
                    items(movies.size, key = { it }) { index ->
                        if (movies.isEmpty()) {
                            FavoritesEmptyUi()
                        } else {
                            favoriteMoviesItem(
                                movie = movies[index],
                                onItemClick = {
                                    openDetail(
                                        "detail/{id}".replace(
                                            oldValue = "{id}",
                                            newValue = it.toString()
                                        )
                                    )
                                }
                                ,
                                onRemoveClick = {
                                    removeFavoriteClicked(it)
                                } )
                        }

                    }
                }

            }
        }
    )


}

@Composable
fun favoriteMoviesItem(movie: FavoritesEntity, onItemClick: (Int) -> Unit,onRemoveClick : (Int) -> Unit) {
    // Movie item UI

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shadowElevation = 2.dp,
        color = White,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier
            .width(120.dp)
            .clickable { onItemClick(movie.id) }) {
            Box{
                loadImage(url = movie.posterPath, modifier = Modifier) {
                    onItemClick(movie.id)
                }

                Icon(
                    Icons.Filled.Favorite, "Favori",
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Sağ üst köşeye hizalama
                        .padding(8.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .clickable { onRemoveClick(movie.id) }
                )


            }
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = movie.title,
                    style = TextStyle.Default,
                    fontSize = 14.sp,
                    color = Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(40.dp)
                )
            }
        }

    }
}