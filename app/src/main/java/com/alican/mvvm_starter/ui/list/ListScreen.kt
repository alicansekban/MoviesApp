package com.alican.mvvm_starter.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.ui.theme.Black
import com.alican.mvvm_starter.ui.theme.Gray
import com.alican.mvvm_starter.ui.theme.White

@Composable
fun ListScreen(
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    viewModel: MoviesListViewModel = hiltViewModel()
) {

    val searchQuery = remember { mutableStateOf("") }
    val movies = viewModel.movies.collectAsLazyPagingItems()

    statelessList(openDetail, popBackStack, viewModel, searchQuery, movies)


}

@Composable
fun statelessList(
    openDetail: (String) -> Unit,
    popBackStack: (String) -> Unit,
    viewModel: MoviesListViewModel = hiltViewModel(),
    searchQuery: MutableState<String> = remember {
        mutableStateOf("")
    },
    movies: LazyPagingItems<MovieUIModel>
) {
    Scaffold(
        topBar = {
            TopBar(title = viewModel.setTitle(), showBackButton = true) {
                popBackStack("-1")
            }
        },

        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = searchQuery.value, onValueChange = {
                        searchQuery.value = it
                        if (it.length > 2) {
                            viewModel.searchMovies(it)
                        } else {
                            viewModel.getData(viewModel.argument)
                        }

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

@Composable
fun TopBar(title: String, showBackButton: Boolean, onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Gray),
        color = White
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Black
                    )
                }
            }

            Text(
                text = title,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Black
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}