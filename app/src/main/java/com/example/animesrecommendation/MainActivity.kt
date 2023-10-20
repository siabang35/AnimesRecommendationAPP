package com.example.animesrecommendation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import java.lang.Math.abs
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animesrecommendation.ui.theme.AnimesRecommendationTheme

// Data class untuk merepresentasikan objek Anime yang saya rekomendasikan
data class Anime(val id: Int, val title: String, val imageResourceId: Int, val rating: Float)

// Kelas ViewModel untuk menyimpan daftar Anime
class AnimeViewModel : ViewModel() {
    var animeList: List<Anime>
        private set
    // Inisialisasi daftar Anime di dalam ViewModel yang saya terapkan
    init {
        animeList = listOf(
            Anime(1, "Naruto", R.drawable.naruto, 4.5f),
            Anime(2, "One Piece", R.drawable.onepiece, 4.8f),
            Anime(3, "Bleach", R.drawable.bleach, 4.2f),
            Anime(4, "Dragon Ball", R.drawable.dragonball, 4.7f),
            Anime(5, "Boku No Pico", R.drawable.bokunopico, 3.0f),
            Anime(6, "Demon Slayer", R.drawable.demon, 4.9f),
            Anime(7, "Jujutsu No Kaisen", R.drawable.jujutsunokaisen, 4.6f),
            Anime(8, "Gintama", R.drawable.gintama, 4.4f),
            Anime(9, "Hunter x Hunter", R.drawable.hunterx, 4.7f),
            Anime(10, "One Punch Man", R.drawable.onepunchman, 4.5f),
            Anime(11, "Final Alchemist", R.drawable.alchemist, 4.3f),
            Anime(12, "Shingeki No Kyujin", R.drawable.snk, 4.8f),
            Anime(13, "Spy x Family", R.drawable.spyx, 4.6f)

        )
    }
  //function List digunakan untuk list image
    fun setAnimeList(list: List<Anime>) {
        animeList = list
    }
}
//penggunaan layout Row dan fitur clickable
// Komponen UI untuk menampilkan item Anime
@Composable
fun AnimeListItem(anime: Anime, onItemClick: () -> Unit) {
    var isHovered by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onItemClick() }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    // Implementasikan efek hover berdasarkan nilai pan, zoom, dan rotation
                    isHovered = abs(pan.x) > 10f || abs(pan.y) > 10f || abs(zoom - 1f) > 0.1f || abs(rotation) > 5f
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            val borderWidth = 4.dp
            Image(
                painter = painterResource(id = anime.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .border(
                        BorderStroke(borderWidth, Color.Yellow),
                        CircleShape
                    )
                    .padding(borderWidth)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = anime.title,
                fontSize = 20.sp,
                color = Color.Black,
            )
            Text(
                text = "Rating: ${anime.rating}",
                fontSize = 16.sp,
                color = Color.Blue,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeList(animeViewModel: AnimeViewModel = viewModel()) {
    val animeList = remember { animeViewModel.animeList }
    LazyColumn {
        items(animeList) { anime ->
            AnimeListItem(anime = anime) {
                // Implementasikan tindakan saat item anime diklik di sini
            }
        }
    }
}
// Fungsi preview untuk menampilkan tata letak secara visual
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivity(animeList: List<Anime>) {
    val viewModel: AnimeViewModel = viewModel()
    viewModel.setAnimeList(animeList)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Blue),

            title = {
                Text(
                    text = "Anime Recommendations",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable { /* Handle navigation icon click */ }
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.clickable { /* Handle actions click */ }
                )
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnimeList(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimesRecommendationTheme {
        val sampleAnimeList = listOf(
            Anime(1, "Naruto", R.drawable.naruto, 4.5f),
            Anime(2, "One Piece", R.drawable.onepiece, 4.8f),
            Anime(3, "Bleach", R.drawable.bleach, 4.2f),
            Anime(4, "Dragon Ball", R.drawable.dragonball, 4.7f),
            Anime(5, "Boku No Pico", R.drawable.bokunopico, 3.0f),
            Anime(6, "Demon Slayer", R.drawable.demon, 4.9f),
            Anime(7, "Jujutsu No Kaisen", R.drawable.jujutsunokaisen, 4.6f),
            Anime(8, "Gintama", R.drawable.gintama, 4.4f),
            Anime(9, "Hunter x Hunter", R.drawable.hunterx, 4.7f),
            Anime(10, "One Punch Man", R.drawable.onepunchman, 4.5f),
            Anime(11, "Final Alchemist", R.drawable.alchemist, 4.3f),
            Anime(12, "Shingeki No Kyujin", R.drawable.snk, 4.8f),
            Anime(13, "Spy x Family", R.drawable.spyx, 4.6f)
        )
        MainActivity(animeList = sampleAnimeList)
    }
}