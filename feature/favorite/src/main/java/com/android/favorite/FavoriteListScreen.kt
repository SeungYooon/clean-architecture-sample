package com.android.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.android.core.model.PokemonModel
import com.android.core.navigation.DetailNav
import com.android.core.util.buildPokemonImageUrl

@Composable
fun FavoriteListScreen(
    navController: NavController,
    viewModel: FavoriteViewModel,
) {
    val favorites by viewModel.favorites.collectAsState()

    FavoriteListScreenContent(
        favorites = favorites,
        onClick = { pokemon ->
            navController.navigate(DetailNav.routeWithArgs(pokemon.id, isFavorite = true))
        }
    )
}

@Composable
fun FavoriteListScreenContent(
    favorites: List<PokemonModel>,
    onClick: (PokemonModel) -> Unit
) {
    val listState = rememberLazyGridState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Favorite",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (favorites.isEmpty()) {
                Text(
                    text = "즐겨찾기한 포켓몬이 없습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    state = listState,
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(favorites) { pokemon ->
                        FavoriteItem(
                            pokemon = pokemon,
                            onClick = { onClick(pokemon) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FavoriteItem(pokemon: PokemonModel, onClick: () -> Unit) {
    val painter = rememberAsyncImagePainter(
        model = pokemon.imageUrl,
        contentScale = ContentScale.Crop
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
            ) {

                Image(
                    painter = painter,
                    contentDescription = pokemon.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = pokemon.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteListScreenContentPreview() {
    val sampleList = listOf(
        PokemonModel(
            id = 1,
            name = "Bulbasaur",
            imageUrl = buildPokemonImageUrl(25),
            isFavorite = true
        ),
        PokemonModel(
            id = 4,
            name = "Charmander",
            imageUrl = buildPokemonImageUrl(25),
            isFavorite = true
        ),
        PokemonModel(
            id = 7,
            name = "Squirtle",
            imageUrl = buildPokemonImageUrl(25),
            isFavorite = true
        )
    )

    FavoriteListScreenContent(
        favorites = sampleList,
        onClick = {}
    )
}
