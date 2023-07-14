package com.alican.mvvm_starter.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.data.local.model.ReviewsEntity
import com.alican.mvvm_starter.domain.model.Cast
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieCreditsUIModel
import com.alican.mvvm_starter.domain.model.MovieDetailUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.home.loadImage
import com.alican.mvvm_starter.ui.list.TopBar

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController
) {


    val viewModel: MovieDetailViewModel = hiltViewModel()
    val movieDetailState by viewModel.movieDetail.collectAsStateWithLifecycle()
    val movieCreditsState by viewModel.movieCredits.collectAsStateWithLifecycle()
    val reviews = viewModel.reviews.collectAsLazyPagingItems()

    val listState = rememberLazyListState()

    LaunchedEffect(movieId) {
        viewModel.getMovieDetail(movieId)
        viewModel.getMovieCredits(movieId)
        viewModel.getReviews(movieId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.txt_movie_detail_title),
                showBackButton = true
            ) {
                navController.navigateUp()
            }
        },
        content = { padding ->
            Box(
                modifier = Modifier.padding(padding).padding(16.dp)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {

                    when (movieDetailState) {
                        is Loading -> {}
                        is Success -> {
                            val movieDetail =
                                (movieDetailState as Success<MovieDetailUIModel>).response
                            item {
                                MovieDetailContent(movieDetail)
                            }
                        }

                        is Error -> {}
                    }
                    when (movieCreditsState) {
                        is Loading -> {}
                        is Success -> {
                            val cast = (movieCreditsState as Success<MovieCreditsUIModel>).response
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
        // Resim
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 16.dp)
        ) {

            loadImage(movieDetail.getImagePath(), modifier = Modifier) {

            }
        }

        // Diğer içerikler
        Text(text = movieDetail.title.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
        //  RatingBar(rating = movieDetail.voteAverage, maxRating = 10f)
        Text(text = movieDetail.releaseDate.toString())
        Text(text = movieDetail.getGenreTexts())
        Text(
            text = movieDetail.overview.toString(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Oyuncu kadrosu
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
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(100.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(30.dp)
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
                    color = Color.Black
                )
                Text(
                    text = cast.character.toString(),
                    color = Color.Black
                )
            }
        }
    }
}


@Composable
fun ReviewItem(review: ReviewsEntity) {

    Card(modifier = Modifier.padding(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            loadImage(review.authorDetails?.getImage().toString(), modifier = Modifier.size(48.dp)) {

            }
            Column(modifier = Modifier.wrapContentHeight()) {
                Text(text = review.authorDetails?.name.toString(), color = Color.Black)
                Text(review.content.toString())
            }
        }
    }
}
