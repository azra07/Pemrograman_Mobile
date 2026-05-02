package com.example.songkpop_compose

import android.R.attr.fontWeight
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(albumId: String?) {
    val album = AlbumDataSource.albumList.find { it.id == albumId }

    if (album != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Image(
                painter = painterResource(id = album.imageResId),
                contentDescription = "Cover Album ${album.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = album.title,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = colorResource(id = R.color.purple_muda)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Artis: ${album.artist}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.gray_text)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text ="${album.year}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.gray_text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = album.description,
                fontSize = 15.sp,
                color = colorResource(id = R.color.dark_gray),
                lineHeight = 24.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Album tidak ditemukan")
        }
    }
}