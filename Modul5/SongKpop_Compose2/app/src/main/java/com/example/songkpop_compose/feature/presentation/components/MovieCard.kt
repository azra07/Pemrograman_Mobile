package com.example.songkpop_compose.feature.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.songkpop_compose.feature.domain.model.Movie

@Composable
fun MovieHorizontalItem(
    movie: Movie,
    onDetailClick: () -> Unit,
    onWebsiteClick: () -> Unit
) {
    val formattedRating = "%.1f".format(movie.rating)

    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onDetailClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            MoviePoster(
                posterPath = movie.posterPath,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                cornerRadius = 8
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "⭐ $formattedRating",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = onDetailClick,
                    modifier = Modifier.weight(1f).height(30.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Detail", fontSize = 10.sp)
                }
                OutlinedButton(
                    onClick = onWebsiteClick,
                    modifier = Modifier.weight(1f).height(30.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Web", fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun MovieVerticalItem(
    movie: Movie,
    onDetailClick: () -> Unit,
    onWebsiteClick: () -> Unit
) {
    val formattedRating = "%.1f".format(movie.rating)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onDetailClick() },
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rilis: ${movie.releaseDate}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Rating: ⭐ $formattedRating",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDetailClick,
                        modifier = Modifier.weight(1f).height(28.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Detail", fontSize = 10.sp)
                    }
                    OutlinedButton(
                        onClick = onWebsiteClick,
                        modifier = Modifier.weight(1f).height(28.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("Website", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun MoviePoster(
    posterPath: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    cornerRadius: Int = 8
) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500$posterPath",
        contentDescription = contentDescription,
        modifier = modifier.clip(RoundedCornerShape(cornerRadius.dp)),
        contentScale = ContentScale.Crop
    )
}