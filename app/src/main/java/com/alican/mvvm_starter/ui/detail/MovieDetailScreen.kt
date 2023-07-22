package com.alican.mvvm_starter.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.alican.mvvm_starter.domain.model.BaseUIModel
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.ui.list.TopBar
import com.alican.mvvm_starter.ui.theme.Black
import com.alican.mvvm_starter.ui.theme.Gray
import com.alican.mvvm_starter.ui.theme.Transparent

@Composable
fun MovieDetailScreen(
    popBackStack: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movieDetailState by viewModel.movieDetail.collectAsStateWithLifecycle()
    val movieCreditsState by viewModel.movieCredits.collectAsStateWithLifecycle()
    val reviews = viewModel.reviews.collectAsLazyPagingItems()



    statelessDetail(
        movieDetailState,
        movieCreditsState,
        reviews,
        popBackStack = { popBackStack(it) },
    onFavoriteClick = {onFavoriteClick(it)})


}

@Composable
fun statelessDetail(
    movieDetailState: BaseUIModel<MovieDetailUIModel>,
    movieCreditsState: BaseUIModel<MovieCreditsUIModel>,
    reviews: LazyPagingItems<ReviewsEntity>,
    listState: LazyListState = rememberLazyListState(),
    popBackStack: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.txt_movie_detail_title),
                showBackButton = true,
                onBackClick = { popBackStack("-1") },
                showFavoriteButton = false,
                onFavoriteClick = { onFavoriteClick("favorites") }
            )
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {

                    when (movieDetailState) {
                        is Loading -> {}
                        is Success -> {
                            val movieDetail =
                                movieDetailState.response
                            item {
                                MovieDetailContent(movieDetail)
                            }
                        }

                        is Error -> {}
                    }
                    when (movieCreditsState) {
                        is Loading -> {}
                        is Success -> {
                            val cast = movieCreditsState.response
                            item {
                                LazyRow(modifier = Modifier.fillMaxWidth()) {
                                    itemsIndexed(
                                        items = cast.cast as ArrayList,
                                        key = { _, cast ->
                                            cast.id!!
                                        }
                                    ) { index, value ->
                                        CastItem(cast = value)
                                        Divider()
                                    }
                                }
                            }
                        }

                        is Error -> {}
                    }

                    item {
                        Text(
                            text = stringResource(R.string.txt_reviews),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }


                    items(
                        items = reviews,
                        key = { it.id }

                    ) { data ->
                        if (data != null) {
                            ReviewItem(review = data)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieDetailContent(movieDetail: MovieDetailUIModel) {
    Column(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 16.dp)
        ) {

            loadImage(movieDetail.getImagePath(), modifier = Modifier) {

            }
        }

        Text(text = movieDetail.title.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = movieDetail.releaseDate.toString())
        Text(text = movieDetail.getGenreTexts())
        Text(
            text = movieDetail.overview.toString(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.txt_cast),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun CastItem(cast: Cast) {
    androidx.compose.material.Card(
        modifier = Modifier
            .width(250.dp)
            .height(100.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(1.dp, Gray)

    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(50.dp))
            ) {
                loadImage(
                    url = cast.getImagePath(), modifier = Modifier
                ) {

                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = cast.name.toString(),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Black
                )
                Text(
                    text = cast.character.toString(),
                    color = Black
                )
            }
        }
    }
}


@Composable
fun ReviewItem(review: ReviewsEntity) {
    androidx.compose.material.Card(
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp),
        backgroundColor = Transparent
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            loadImage(
                review.authorDetails?.getImage().toString(),
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(50.dp))
            ) {

            }
            Column(modifier = Modifier.wrapContentHeight()) {
                Row(
                    horizontalArrangement = Arrangement.Center, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = review.authorDetails?.name.toString(),
                        style = TextStyle.Default,
                        fontSize = 16.sp,
                        color = Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                }

                Spacer(modifier = Modifier.padding(4.dp))
                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp))
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = review.content.toString(),
                    style = TextStyle.Default,
                    fontSize = 12.sp,
                    color = Black,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}
