package com.alican.mvvm_starter.ui.list

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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alican.mvvm_starter.customViews.TopBar
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.ui.theme.Black
import com.alican.mvvm_starter.ui.theme.White
import kotlin.random.Random

@Composable
fun ListScreen(
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    openFavorites: (String) -> Unit,
    viewModel: MoviesListViewModel = hiltViewModel()
) {

    val listState = rememberLazyGridState()

    val movies = viewModel.movies.collectAsLazyPagingItems()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(key1 = searchQuery) {
        if (searchQuery.length > 2) {
            viewModel.searchMovies(searchQuery)
        } else if (searchQuery.isEmpty()) {
            viewModel.getData(viewModel.argument)
        }
    }

    statelessList(
        openDetail = openDetail,
        popBackStack = popBackStack,
        openFavorites = openFavorites,
        onFavoriteClick = {
            viewModel.addToFavorites(it) },
        title = viewModel.setTitle(),
        movies = movies,
        searchQuery = searchQuery,
        onSearchQueryChange = { newValue ->
            searchQuery = newValue
        },
        listState
    )



    val state by viewModel.favorites.collectAsStateWithLifecycle()
    val popupControl by remember { derivedStateOf { state is Success<*> } }
    if (popupControl) {
        popUp(
            onDismissRequest = { viewModel.favoritesEmitted() },
            openFavorites = { openFavorites("favorites") }
        )
    }
}

@Composable
fun statelessList(
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    openFavorites: (String) -> Unit,
    onFavoriteClick: (MovieUIModel) -> Unit,
    title: String,
    movies: LazyPagingItems<MovieUIModel>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    listState : LazyGridState
) {


    Scaffold(
        topBar = {
            TopBar(
                title = title,
                showBackButton = true,
                onBackClick = { popBackStack("-1") },
                showFavoriteButton = true,
                onFavoriteClick = { openFavorites("favorites") })
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery, onValueChange = {
                        onSearchQueryChange(it)

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    state = listState
                ) {
                    items(movies.itemCount, key = { movies[it]?.id ?: Random.nextInt().toString() }) { index ->
                        MovieItem(movie = movies[index]!!,
                            onFavoriteClick = {
                                onFavoriteClick(it)

                            },
                            onItemClick = {
                                openDetail(
                                    "detail/{id}".replace(
                                        oldValue = "{id}",
                                        newValue = it.toString()
                                    )
                                )
                            })
                    }
                    item {
                        if (movies.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }

                }

            }
        }
    )
}

@Composable
fun MovieItem(
    movie: MovieUIModel,
    onItemClick: (Int) -> Unit,
    onFavoriteClick: (MovieUIModel) -> Unit
) {
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
                loadImage(url = movie.getImagePath(), modifier = Modifier) {
                    onItemClick(movie.id)
                }

                Icon(
                    Icons.Filled.Favorite, "Favori",
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Sağ üst köşeye hizalama
                        .padding(8.dp)
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .clickable { onFavoriteClick(movie) }
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


@Composable
fun popUp(
    onDismissRequest: () -> Unit,
    openFavorites: () -> Unit
) {
    Popup(
        alignment = Alignment.Center,
        offset = IntOffset(0, 0),
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth(0.72f),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clickable {
                            onDismissRequest()
                        }, horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Movie added to your favorites",
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center
                    )
                }
                Divider(Modifier.fillMaxWidth())
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clickable {
                            openFavorites()
                            onDismissRequest()
                        },
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Go to favorites",
                        modifier = Modifier
                            .padding(top = 10.dp),
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
    }

}
