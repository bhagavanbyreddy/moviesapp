@file:OptIn(ExperimentalMaterial3Api::class)

package org.themoviedb.example.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.themoviedb.example.R
import org.themoviedb.example.home.model.Results
import org.themoviedb.example.home.viewModel.HomeViewModel
import org.themoviedb.example.util.NavigationConstants
import org.themoviedb.example.util.ShimmerGridItem
import org.themoviedb.example.util.rememberLifecycleEvent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    var searchText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.searchQuery("")
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Surface {
        Box(
            modifier = Modifier
                .background(color = colorResource(R.color.teal_200))
                .fillMaxSize()
        ) {
            Column {

                SearchTextField(
                    keyboardController = keyboardController!!,
                    focusManager = focusManager,
                    enabled = true,
                    onSearch = {
                        viewModel.searchQuery(it.text.trim())
                        searchText = it
                    },
                    onClear = {
                        viewModel.searchQuery("")
                        searchText = TextFieldValue("")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if(state.playlists.size == 0){
                    Column {
                        repeat(8) {
                            ShimmerGridItem(brush = linearGradient(
                                listOf(
                                    Color.LightGray.copy(alpha = 0.9f),
                                    Color.LightGray.copy(alpha = 0.4f),
                                    Color.LightGray.copy(alpha = 0.9f)
                                )
                            ))
                        }
                    }
                }
                ShowsListsUi(
                    navController = navController,
                    list = state.playlists,
                )
            }
        }
    }


}

@Composable
fun ShowsListsUi(
    navController: NavController,
    list: ArrayList<Results>,
) {

    LazyColumn(
    ) {

        items(list.size, itemContent = {

            ShowItems(
                navController = navController,
                playlist = list[it],
            )
        })
    }

}


@Composable
fun ShowItems(
    navController: NavController,
    playlist: Results,
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
                    //.width(168.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.TopEnd,

            ) {
                AsyncImage(
                    alignment = Alignment.Center,
                    model = "https://image.tmdb.org/t/p/w342/${playlist.backdropPath}",
                    contentDescription = playlist.overview,
                    //error = painterResource(id = R.drawable.ic_app_placeholder_thumbnail),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                    //.clip(RoundedCornerShape(5.dp)),
                    //placeholder = painterResource(id = R.drawable.ic_app_placeholder_thumbnail)
                )
                if (playlist.isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )

                }
            }
            Text(
                text = playlist.title ?: playlist.name ?: "Not available",
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(2f)
                    .padding(top = 5.dp)
            )

        }
    }
}
