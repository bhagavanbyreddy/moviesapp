package org.themoviedb.example.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.themoviedb.example.R
import org.themoviedb.example.util.NavigationConstants


@Composable
fun SplashScreen(navController: NavController, viewModel: SplashViewModel = viewModel()) {

    val isLoading by viewModel.isLoading.collectAsState()


    LaunchedEffect(key1 = isLoading) {
        if (!isLoading) {
            navController.navigate(NavigationConstants.HOME)
        }
    }

    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.teal_200)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = "logo",
                    modifier = Modifier
                        .width(600.dp)
                        .height(600.dp)
                )
            }
        }
    }
}