package com.alican.mvvm_starter.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.domain.model.MovieUIModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    type: String? = null,
    viewModel: MoviesListViewModel = hiltViewModel()
) {

    val searchQuery = remember { mutableStateOf("") }

    val movies = viewModel.movies.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(text = type.toString())
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

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    state = rememberLazyListState()
                ) {
                    itemsIndexed(
                        items = movies,
                        key = { _, movie ->
                            movie.id
                        }
                    ) { index, value ->
                        MovieItem(movie = value) { id ->
                            navController.navigate("detail/{$id}")
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun MovieItem(movie: MovieUIModel?, onItemClick: (Int) -> Unit) {
    // Movie item UI
    // Öğe görünümünü oluşturun ve onItemClick işlevini çağırarak tıklama olayını işleyin
    // Örneğin, Card veya Box gibi bir Container kullanabilir ve içindeki verileri görüntüleyebilirsiniz
    // Örneğin:
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(movie!!.id) }
    ) {
        Text(
            text = movie!!.title,
            modifier = Modifier.fillMaxWidth()
        )
        // Öğe içeriği (örneğin, afiş, başlık, vb.) burada görüntülenebilir
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