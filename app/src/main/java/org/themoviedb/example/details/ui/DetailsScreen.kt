@file:OptIn(ExperimentalMaterial3Api::class)

package org.themoviedb.example.details.ui

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.themoviedb.example.R
import org.themoviedb.example.details.model.DetailsModel
import org.themoviedb.example.details.model.Seasons
import org.themoviedb.example.details.viewModel.DetailsViewModel
import org.themoviedb.example.home.model.Results
import org.themoviedb.example.util.NavigationConstants

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailsScreen(navController: NavController, viewModel: DetailsViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Surface {
        Box(
            modifier = Modifier
                .background(color = colorResource(id = R.color.teal_200))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Header(
                    navController = navController,
                    title = state.details.name ?: "Movies Db",
                    viewModel = viewModel,
                    id = state.details.id ?: -1,
                    isFav = state.isFavorite
                )
                Spacer(modifier = Modifier.height(40.dp))
                Poster(detailsData = state.details)
                OverView(detailsData = state.details)
                Spacer(modifier = Modifier.height(60.dp))
                SimilarShowsListUi(
                    list = state.similarList?.results ?: arrayListOf(),
                    navController = navController
                )

            }
        }
    }
}

// poster composable
@Composable
fun Poster(detailsData: DetailsModel) {
    Box(
        modifier = Modifier
            .aspectRatio(16 / 9f)
            .fillMaxWidth()
            .shadow(elevation = 16.dp)
            .padding(16.dp)
    ) {
        AsyncImage(
            alignment = Alignment.Center,
            model = "https://image.tmdb.org/t/p/w342/${detailsData.backdropPath}",
            contentDescription = detailsData.overview,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun OverView(detailsData: DetailsModel) {
    Box(modifier = Modifier.padding(16.dp)) {
        Column {
            Text(
                text = "Overview",
                fontWeight = FontWeight(weight = 700),
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = detailsData.overview.toString(),
                style = TextStyle(color = Color.Black),
                maxLines = 15
            )
        }
    }

}

@Composable
fun RelatedSeasonsUi(
    detailsData: DetailsModel,
) {
    Column {
        Text(
            text = "Seasons",
            fontWeight = FontWeight(weight = 700),
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 16.dp)
        )
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp), content = {
            items(items = detailsData.seasons, itemContent = {
                ShowsContentUi(
                    playlist = it,
                )
            })
        })
    }
}

@Composable
fun SimilarContentItemUi(
    playlist: Results,
    navController: NavController
) {
    ElevatedCard(
        modifier = Modifier.padding(8.dp), shape = RoundedCornerShape(16.dp),

        ) {
        Column(modifier = Modifier
            .height(140.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    navController.navigate(NavigationConstants.DETAILS + "/${playlist.id}")
                }
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(109.dp)
                    .width(168.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(5.dp))
                    .shadow(elevation = 8.dp)
            ) {
                AsyncImage(
                    alignment = Alignment.Center,
                    model = "https://image.tmdb.org/t/p/w342/${playlist.posterPath}",
                    contentDescription = playlist.overview,
                    //error = painterResource(id = R.drawable.ic_app_placeholder_thumbnail),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Text(
                text = playlist.name ?: "Not available",
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(2f)
                    .padding(top = 5.dp)
                    .width(168.dp)
            )

        }
    }
}


@Composable
fun SimilarShowsListUi(
    list: ArrayList<Results>,
    navController: NavController
) {
    Text(
        text = "Similar Shows",
        fontWeight = FontWeight(weight = 700),
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 16.dp)
    )
    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp), content = {
        items(items = list, itemContent = {

           SimilarContentItemUi(
                playlist = it,
                navController = navController
            )
        })
    })

}

@Composable
fun ShowsContentUi(
    playlist: Seasons,
) {
    ElevatedCard(
        modifier = Modifier.padding(8.dp), shape = RoundedCornerShape(16.dp),

        ) {
        Column(modifier = Modifier
            .height(140.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {
                    //if (!isEditable) navController.navigate(NavigationConstants.HOME + "/${playlist.content?.key}/${playlist.content?.contentType}")
                }
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(109.dp)
                    .width(168.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(5.dp))
                    .shadow(elevation = 8.dp)
            ) {
                AsyncImage(
                    alignment = Alignment.Center,
                    model = "https://image.tmdb.org/t/p/w342/${playlist.posterPath}",
                    contentDescription = playlist.overview,
                    //error = painterResource(id = R.drawable.ic_app_placeholder_thumbnail),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Text(
                    text = (playlist.episodeCount.toString()) + " Episodes",
                    color = Color.White,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(Color.Black)
                        .alpha(0.8f)
                )
            }
            Text(
                text = playlist.name ?: "Not available",
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(2f)
                    .padding(top = 5.dp)
                    .width(168.dp)
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    navController: NavController,
    title: String,
    viewModel: DetailsViewModel,
    id: Int,
    isFav: Boolean
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            Box(modifier = Modifier.padding(16.dp)) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                        .clickable(onClick = {
                            navController.popBackStack()
                        })
                )
            }
        },
        actions = {
            IconButton(onClick = {

                if (isFav) {
                    viewModel.removeToFavorite(id = id)
                } else {
                    viewModel.addToFavorite(id = id)
                }

            }) {
                if (isFav) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.Red
                    )

                } else {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.White
                    )

                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Black
        )
    )

}