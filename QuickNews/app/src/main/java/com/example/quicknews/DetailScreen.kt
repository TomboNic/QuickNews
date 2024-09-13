package com.example.quicknews

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@Composable
fun DetailScreen(article: Article) {

    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
    val bottomBarColor = MaterialTheme.colorScheme.background

    val context = LocalContext.current
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            window.statusBarColor = backgroundColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

            window.navigationBarColor = bottomBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }

    Scaffold(
        topBar = { DetailTopBar() }
    ) { paddingValues ->
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Body(article = article)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "The New York Times",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp
                )
            }
        },
        actions = {
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(19.dp, 17.dp)
            )
        },
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
    )
}

@Composable
fun Body(article: Article) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(text = article.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        article.author?.let { Text(text = it, color = Color(0xFF637587)) }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = article.publishedAt.substring(0, 10), color = Color(0xFF637587))
        Spacer(modifier = Modifier.height(15.dp))
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier.size(390.dp, 248.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(15.dp))
        article.content?.substring(0, 200).let {
            if (it != null) {
                Text(text = it)
            }
        }
        article.content?.substring(0, 200).let {
            if (it != null) {
                Text(text = it)
            }
        }
        article.content?.substring(0, 200).let {
            if (it != null) {
                Text(text = it)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        ViewMoreButton {}
    }
}

@Composable
fun ViewMoreButton(onViewMoreClick: () -> Unit) {
    Button(
        onClick = { onViewMoreClick },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1A80E5)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(15.dp, 12.dp),
            tint = Color.White
        )
        Text(text = "Read More", textAlign = TextAlign.Center)
    }
}
