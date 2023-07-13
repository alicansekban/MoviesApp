package com.alican.mvvm_starter.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.util.Constant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    type: String? = null
) {

    val searchQuery = remember { mutableStateOf("") }

    val viewModel: MoviesListViewModel = hiltViewModel()

    val movies = viewModel.movies.collectAsLazyPagingItems()

    LaunchedEffect(key1 = type) {
        viewModel.getData(type)
    }
    val title: String = when (type) {
        Constant.POPULAR_MOVIES -> {
            stringResource(id = R.string.txt_popular_movies)
        }

        Constant.UP_COMING_MOVIES -> {
            stringResource(id = R.string.txt_upcoming_movies)
        }

        Constant.TOP_RATED_MOVIES -> {
            stringResource(id = R.string.txt_top_rated_movies)
        }

        Constant.NOW_PLAYING -> {
            stringResource(id = R.string.txt_now_playing_movies)
        }

        else -> {
            ""
        }
    }

    Scaffold(
        topBar = {
            TopBar(title = title, showBackButton = true) {
                navController.navigateUp()
            }
        },

        content = { padding ->
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = searchQuery.value, onValueChange = {
                        searchQuery.value = it
                        if (it.length > 2) {
                            viewModel.searchMovies(it)
                        } else {
                            viewModel.getData(type)
                        }

                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    placeholder = {
                        Text(text = "Search...")
                    },
                    maxLines = 1,
                    singleLine = true
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(movies.itemCount, key = { it }) { index ->
                        MovieItem(movie = movies[index]!!) { id ->
                            navController.navigate(
                                "detail/{id}".replace(
                                    oldValue = "{id}",
                                    newValue = "$id"
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
            .fillMaxWidth().padding(12.dp),
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.White,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(
            modifier = Modifier
                .width(120.dp)
                .padding(top = 16.dp, start = 8.dp)
                .clickable { onItemClick(movie.id) }
        ) {
            loadImage(url = movie.getImagePath(), modifier = Modifier) {
                onItemClick(movie.id)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                style = TextStyle.Default,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }

}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchQuerySubmit: (String) -> Unit
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(searchQuery)) }

    Row(modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            modifier = Modifier.size(24.dp)
        )

        TextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                onSearchQueryChange(it.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            textStyle = TextStyle(fontSize = 14.sp),
            singleLine = true,
            placeholder = { Text(text = "Search") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchQuerySubmit(textFieldValue.text) })
        )
    }

}

@Composable
fun TopBar(title: String, showBackButton: Boolean, onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shadowElevation = 2.dp,
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.White
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
                        tint = Color.Black
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
                    color = Color.Black
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}