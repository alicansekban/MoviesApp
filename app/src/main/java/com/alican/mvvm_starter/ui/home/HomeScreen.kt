package com.alican.mvvm_starter.ui.home

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.domain.model.Error
import com.alican.mvvm_starter.domain.model.Loading
import com.alican.mvvm_starter.domain.model.MovieUIModel
import com.alican.mvvm_starter.domain.model.Success
import com.alican.mvvm_starter.ui.list.TopBar
import com.alican.mvvm_starter.util.Constant
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlin.math.absoluteValue
import kotlin.math.min


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    openList: (String) -> Unit,
    openDetail: (Int) -> Unit
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val popularMovies by viewModel.popularMovies.collectAsStateWithLifecycle()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsStateWithLifecycle()
    val topRatedMovies by viewModel.topRatedMovies.collectAsStateWithLifecycle()
    val upComingMovies by viewModel.upComingMovies.collectAsStateWithLifecycle()

    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.get_home_title),
                showBackButton = false,
            ) {

            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(top = 32.dp)
        ) {

            when (popularMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {

                    val response = (popularMovies as Success<List<MovieUIModel>>).response

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        val pagerState = rememberPagerState()
                        HorizontalPager(
                            pageCount = response.size,
                            state = pagerState,
                            pageSize = PageSize.Fill,
                        ) { index ->
                            Box(modifier = Modifier.graphicsLayer {
                                val pageOffset = pagerState.calculateCurrentOffsetForPage(index)
                                val offScreenRight = pageOffset < 0f
                                val deg = 105f
                                val interpolated = FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                                rotationY = min(interpolated * if (offScreenRight) deg else -deg, 90f)

                                transformOrigin = TransformOrigin(
                                    pivotFractionX = if (offScreenRight) 0f else 1f,
                                    pivotFractionY = .5f
                                )
                            }.fillMaxSize()) {
                                loadImage(
                                    url = response[index].getImagePath(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .height(300.dp)
                                ) {
                                }
                            }


                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 12.dp)
                    )
                    HomeSection(
                        title = stringResource(R.string.txt_popular_movies),
                        movies = response,
                        onSeeAllClick = { openList(Constant.POPULAR_MOVIES) },
                        onItemClick = { openDetail(it) }
                    )
                }
            }
            when (upComingMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_upcoming_movies),
                        movies = (upComingMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList(Constant.UP_COMING_MOVIES) },
                        onItemClick = { openDetail(it) }
                    )
                }
            }
            when (nowPlayingMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_now_playing_movies),
                        movies = (nowPlayingMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList(Constant.NOW_PLAYING) },
                        onItemClick = { openDetail(it) }
                    )
                }
            }
            when (topRatedMovies) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    HomeSection(
                        title = stringResource(R.string.txt_top_rated_movies),
                        movies = (topRatedMovies as Success<List<MovieUIModel>>).response,
                        onSeeAllClick = { openList(Constant.TOP_RATED_MOVIES) },
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )

                Text(
                    text = stringResource(id = R.string.txt_see_all),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { onSeeAllClick() }
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(
                items = movies,
                key = { _, movie ->
                    movie.id
                }
            ) { index, value ->
                HomeMovieItem(movie = value, onItemClick = onItemClick)
            }
        }
    }
}

@Composable
fun HomeMovieItem(movie: MovieUIModel, onItemClick: (Int) -> Unit) {

    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(top = 16.dp, start = 8.dp)
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
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun loadImage(url: String, modifier: Modifier, onItemClick: () -> Unit) {

    // Image'a tıklandığında onItemClick fonksiyonunu tetikleyin
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick() },
        shape = RoundedCornerShape(10.dp)
    ) {
        GlideImage(model = url, contentDescription = "loadImage", modifier = modifier, contentScale = ContentScale.FillBounds) {
            it.error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_foreground)
                .load(url)

        }
    }
}

// extension method for current page offset
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

// ACTUAL OFFSET
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction

// OFFSET ONLY FROM THE LEFT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.startOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtLeast(0f)
}

// OFFSET ONLY FROM THE RIGHT
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.endOffsetForPage(page: Int): Float {
    return offsetForPage(page).coerceAtMost(0f)
}


@Composable
fun HomeScreenToolbar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.get_home_title)) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = MaterialTheme.colors.onPrimary
    )
}
