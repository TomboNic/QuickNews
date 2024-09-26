package com.example.quicknews

import android.app.Activity
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage


@Composable
fun NewsScreen(
    onDetailClick: (Article) -> Unit,
    viewState: NewsViewModel.NewsState,
    currentCategory: String,
    onCategoryChange: (String) -> Unit
) {

    val darkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
    val bottomBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)

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
        topBar = { TopBar(currentCategory, onCategoryChange) },
        bottomBar = { BottomBar(backgroundColor = bottomBarColor) },
        containerColor = backgroundColor
    ) { paddingValues ->
        when {
            viewState.loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }

            viewState.error != null -> {
                Text("ERROR OCCURRED")
            }

            else -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    ContentScreen(viewState.list, onDetailClick)
                }
            }
        }
    }
}

@Composable
fun BottomBar(backgroundColor: Color) {
    Column {
        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            thickness = 0.7.dp,
            modifier = Modifier.fillMaxWidth()
        )
        BottomAppBar(
            containerColor = backgroundColor,
            modifier = Modifier.height(60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomBarIcon(icon = R.drawable.home, contentDescription = "Home")
                Spacer(modifier = Modifier.width(3.dp))
                BottomBarIcon(icon = R.drawable.trending, contentDescription = "Trending")
                Spacer(modifier = Modifier.width(3.dp))
                BottomBarIcon(icon = R.drawable.subscribe, contentDescription = "Subscribe")
                Spacer(modifier = Modifier.width(3.dp))
                BottomBarIcon(icon = R.drawable.profile, contentDescription = "Profile")
            }
        }
    }
}

@Composable
fun BottomBarIcon(icon: Int, contentDescription: String) {
    Icon(
        painter = painterResource(id = icon),
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .size(20.dp)
            .clickable { /* Handle click */ }
    )
}


@Composable
fun TopBar(currentCategory: String, onCategoryChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "News",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(55.dp))
            Icon(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "Notifications",
                tint = Color.Black,
                modifier = Modifier
                    .size(24.dp)
                    .weight(1f, fill = false)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE8EDF2),
                unfocusedContainerColor = Color(0xFFE8EDF2),
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .height(48.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    tint = Color(0xFF4F7396),
                    modifier = Modifier
                        .size(20.dp)
                        .padding(2.dp)
                )
            },
            placeholder = { Text(text = "Search News", fontSize = 13.sp) }
        )
        TopicsRow(currentCategory, onCategoryChange)
    }

}

@Composable
fun NewsItems(article: Article, onDetailClick: (Article) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onDetailClick(article) }
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = 100f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = article.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            article.description?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun TopicsRow(currentCategory: String, onCategoryChange: (String) -> Unit) {
    val categories =
        listOf("general", "technology", "business", "health", "sports", "entertainment")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(categories) { category ->
            RoundedButton(
                text = category.capitalize(),
                isSelected = category == currentCategory,
                onClick = { onCategoryChange(category) }
            )
        }
    }
}

@Composable
fun RoundedButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(40),
        modifier = Modifier
            .padding(8.dp)
            .height(34.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color(0xFFE8EDF2)
        )
    ) {
        Text(text = text, color = if (isSelected) Color.White else Color.Black)
    }
}

@Composable
fun ContentScreen(articleList: List<Article>, onDetailClick: (Article) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(articleList) { articles ->
            if (articles.title != "[Removed]" && articles.urlToImage != null) {
                NewsItems(articles, onDetailClick)
            }
        }
    }
}

@Composable
fun ErrorView(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error: $error", color = Color.Red)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}