package com.ibrahimutkusarican.cleanarchitecturemovieapp.feature.seeall.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.paging.compose.LazyPagingItems
import com.ibrahimutkusarican.cleanarchitecturemovieapp.R
import com.ibrahimutkusarican.cleanarchitecturemovieapp.ui.common.screen.EmptyScreenType
import com.ibrahimutkusarican.cleanarchitecturemovieapp.feature.seeall.domain.model.SeeAllMovieModel
import com.ibrahimutkusarican.cleanarchitecturemovieapp.ui.common.base.BasePagingComposable
import com.ibrahimutkusarican.cleanarchitecturemovieapp.utils.helper.fontDimensionResource
import com.ibrahimutkusarican.cleanarchitecturemovieapp.ui.common.widget.MovieImage

@Composable
fun SeeAllMovies(
    modifier: Modifier = Modifier,
    pagingMovies: LazyPagingItems<SeeAllMovieModel>,
    handleUiActions: (SeeAllUiAction) -> Unit = {}
) {
    BasePagingComposable(pagingItems = pagingMovies, emptyScreenType = EmptyScreenType.SEARCH) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.large_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.medium_padding)),
        ) {
            items(count = pagingMovies.itemCount,
                key = { index -> index }) { index ->
                pagingMovies[index]?.let { movie ->
                    SeeAllMovieItem(
                        seeAllMovie = movie,
                        movieClickAction = { movieId ->
                            handleUiActions(SeeAllUiAction.MovieClick(movieId))
                        }
                    )
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun SeeAllMovieItem(
    modifier: Modifier = Modifier,
    seeAllMovie: SeeAllMovieModel = SeeAllMovieModel(
        movieId = 0,
        movieTitle = "Spiderman No Way Home",
        movieContent = LoremIpsum(50).values.joinToString(),
        moviePosterImageUrl = "",
        movieGenres = listOf("Action", "Scintfic", "Drama"),
        movieTMDBScore = 7.4,
        movieReleaseTime = "2022-12-15"
    ),
    movieClickAction: (movieId: Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { movieClickAction(seeAllMovie.movieId) }, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.see_all_category_movie_width))
        ) {
            Card(
                shape = RoundedCornerShape(dimensionResource(R.dimen.medium_border))
            ) {
                MovieImage(
                    imageUrl = seeAllMovie.moviePosterImageUrl,
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.see_all_category_movie_width))
                        .height(dimensionResource(R.dimen.see_all_category_movie_width))
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = dimensionResource(R.dimen.medium_padding)),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = seeAllMovie.movieTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = fontDimensionResource(R.dimen.movie_category_item_title_size),
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = seeAllMovie.movieContent,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = fontDimensionResource(R.dimen.see_all_movie_item_content_text_size)
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}
