package com.alican.mvvm_starter.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alican.mvvm_starter.customViews.TopBar
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.ui.theme.Black
import com.alican.mvvm_starter.ui.theme.White

@Composable
fun ListScreen(
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    openFavorites: (String) -> Unit,
    viewModel: MoviesListViewModel = hiltViewModel()
) {

    val movies = viewModel.movies.collectAsLazyPagingItems()

    val searchQuery: MutableState<String> = remember { mutableStateOf("") }


    LaunchedEffect(key1 = searchQuery.value) {
        if (searchQuery.value.length > 2) {
            viewModel.searchMovies(searchQuery.value)
        } else if (searchQuery.value.isEmpty()) {
            viewModel.getData(viewModel.argument)
        }
    }

    statelessList(
        openDetail,
        popBackStack,
        openFavorites,
        viewModel.setTitle(),
        movies = movies,
        searchQuery = searchQuery.value,
        onSearchQueryChange = { newValue ->
            searchQuery.value = newValue
        }
    )


}

@Composable
fun statelessList(
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    openFavorites: (String) -> Unit,
    title: String,
    movies: LazyPagingItems<MovieUIModel>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit

) {
    Scaffold(
        topBar = {
            TopBar(
                title = title,
                showBackButton = true,
                onBackClick = { popBackStack("-1") },
                showFavoriteButton = false,
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
                    columns = GridCells.Fixed(2)
                ) {
                    items(movies.itemCount, key = { it }) { index ->
                        MovieItem(movie = movies[index]!!) { id ->
                            openDetail(
                                "detail/{id}".replace(
                                    oldValue = "{id}",
                                    newValue = id.toString()
                                )
                            )
                        }
                    }
                }

            }
        }
    )
}

@Composable
fun MovieItem(movie: MovieUIModel, onItemClick: (Int) -> Unit) {
    // Movie item UI

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shadowElevation = 2.dp,
        color = White,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .width(120.dp)
                .clickable { onItemClick(movie.id) }
        ) {
            loadImage(url = movie.getImagePath(), modifier = Modifier) {
                onItemClick(movie.id)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
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
