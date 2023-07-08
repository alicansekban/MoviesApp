package com.alican.mvvm_starter.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
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
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(text = title)
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(16.dp)) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    searchQuery = searchQuery.value,
                    onSearchQueryChange = { searchQuery.value = it },
                    onSearchQuerySubmit = { query ->
                        if (query.length > 2) {
                            viewModel.searchMovies(query = query)
                        } else {
                            viewModel.getData(type)
                        }
                    }
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(movies.itemCount) { index ->
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
    // Öğe görünümünü oluşturun ve onItemClick işlevini çağırarak tıklama olayını işleyin
    // Örneğin, Card veya Box gibi bir Container kullanabilir ve içindeki verileri görüntüleyebilirsiniz
    // Örneğin:
    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(top = 16.dp, start = 8.dp)
            .clickable { onItemClick(movie.id) }
    ) {
        loadImage(url = movie.getImagePath()) {
            onItemClick(movie.id)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = TextStyle.Default,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
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