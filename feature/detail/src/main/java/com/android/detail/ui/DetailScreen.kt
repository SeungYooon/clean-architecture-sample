package com.android.detail.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.common.model.PokemonDetailModel
import com.android.core.component.InfoCard
import com.android.core.util.FavoriteStatusUpdater
import com.android.core.util.buildPokemonImageUrl
import com.android.detail.state.PokemonDetailUiState
import com.android.detail.viewmodel.DetailViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    detailViewModel: DetailViewModel,
    favoriteStatusUpdater: FavoriteStatusUpdater,
    pokemonId: Int,
    isFavorite: Boolean
) {
    val pokemonState by detailViewModel.pokemonState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pokemonId) {
        detailViewModel.loadPokemonDetail(pokemonId, isFavorite)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isFavorite) "Pokemon Favorite"
                        else "Pokemon Detail"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (pokemonState) {
                is PokemonDetailUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is PokemonDetailUiState.Error -> {
                    val message = (pokemonState as PokemonDetailUiState.Error).message
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: $message\nPlease check your network connection.",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is PokemonDetailUiState.Success -> {
                    val pokemon = (pokemonState as PokemonDetailUiState.Success).pokemon

                    DetailScreenContent(
                        pokemon = pokemon,
                        isFavorite = isFavorite,
                        addFavorite = {
                            if (!isFavorite) {
                                detailViewModel.addToFavorites(
                                    onSuccess = {
                                        favoriteStatusUpdater.updatePokemonFavoriteStatus(
                                            pokemonId,
                                            true
                                        )
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar("즐겨찾기에 추가됨")
                                        }
                                    },
                                    onFailure = { message ->
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar("추가 실패: $message")
                                        }
                                    }
                                )
                            }
                        },
                        removeFavorite = {
                            if (isFavorite) {
                                detailViewModel.removeFromFavorites(
                                    onSuccess = {
                                        favoriteStatusUpdater.updatePokemonFavoriteStatus(
                                            pokemonId,
                                            false
                                        )
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar("즐겨찾기에서 제거됨")
                                        }
                                    },
                                    onFailure = { message ->
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar("제거 실패: $message")
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreenContent(
    pokemon: PokemonDetailModel,
    isFavorite: Boolean,
    addFavorite: () -> Unit,
    removeFavorite: () -> Unit
) {
    val imageUrl = buildPokemonImageUrl(pokemon.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF3F4F6)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier.fillMaxSize(0.7f),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("타입", style = MaterialTheme.typography.labelMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            pokemon.types.forEach {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF1976D2),
                    contentColor = Color.White
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("기본 정보", style = MaterialTheme.typography.labelMedium)

        InfoCard(label = "키", value = "${pokemon.height}m")
        InfoCard(label = "몸무게", value = "${pokemon.weight}kg")

        Spacer(modifier = Modifier.weight(1f))

        val buttonColor = if (isFavorite) Color(0xFFD32F2F) else Color(0xFF388E3C)
        val buttonText = if (isFavorite) "즐겨찾기 삭제" else "즐겨찾기 추가"
        val buttonIcon = if (isFavorite) Icons.Default.Close else Icons.Default.Favorite

        Button(
            onClick = if (isFavorite) removeFavorite else addFavorite,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Icon(buttonIcon, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(buttonText, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenContentPreview() {
    val samplePokemon = PokemonDetailModel(
        id = 25,
        name = "피카츄",
        height = 0,
        weight = 6,
        types = listOf("Electric"),
        imageUrl = buildPokemonImageUrl(25),
        isFavorite = false
    )

    DetailScreenContent(
        pokemon = samplePokemon,
        isFavorite = false,
        addFavorite = {},
        removeFavorite = {}
    )
}
