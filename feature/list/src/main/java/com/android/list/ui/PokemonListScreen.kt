package com.android.list.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.android.common.model.PokemonModel
import com.android.core.component.ErrorContent
import com.android.core.navigation.DetailNav
import com.android.core.util.buildPokemonImageUrl
import com.android.list.state.PokemonListUiState
import com.android.list.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel
) {
    val pokemonPagingItems = viewModel.pokemonPagingData.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    val onItemClick: (PokemonModel) -> Unit = { pokemon ->
        navController.navigate(DetailNav.routeWithArgs(pokemon.id, isFavorite = false))
    }

    val onToggleFavorite: (PokemonModel) -> Unit = { pokemon ->
        viewModel.toggleFavorite(pokemon)
    }

    when (uiState) {
        is PokemonListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PokemonListUiState.Error -> {
            val message = (uiState as PokemonListUiState.Error).message
            ErrorContent(message)
        }

        is PokemonListUiState.Success -> {
            PokemonListScreenContent(
                pagingData = pokemonPagingItems,
                onItemClick = onItemClick,
                onToggleFavorite = onToggleFavorite
            )
        }
    }
}

@Composable
fun PokemonListScreenContent(
    pagingData: LazyPagingItems<PokemonModel>,
    onItemClick: (PokemonModel) -> Unit,
    onToggleFavorite: (PokemonModel) -> Unit,
) {
    val listState = rememberLazyGridState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "List",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                .align(Alignment.Start)
        )

        Box(modifier = Modifier.fillMaxSize()) {
            when (pagingData.loadState.refresh) {
                is LoadState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is LoadState.Error -> {
                    val error =
                        (pagingData.loadState.refresh as LoadState.Error).error.message
                            ?: "알 수 없는 오류"
                    ErrorContent(message = error)
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        state = listState,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        items(pagingData.itemCount) { index ->
                            pagingData[index]?.let { pokemon ->
                                PokemonItem(
                                    pokemon = pokemon,
                                    onClick = { onItemClick(pokemon) },
                                    onFavoriteClick = { onToggleFavorite(pokemon) }
                                )
                            }
                        }

                        if (pagingData.loadState.append is LoadState.Loading) {
                            item(span = { GridItemSpan(3) }) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.5f))
                                }
                            }
                        }

                        if (pagingData.loadState.append is LoadState.Error) {
                            val error =
                                (pagingData.loadState.append as LoadState.Error).error.message
                                    ?: "로드 실패"
                            item(span = { GridItemSpan(3) }) {
                                ErrorContent(message = error)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: PokemonModel,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = pokemon.imageUrl,
        contentScale = ContentScale.Crop
    )

    val state = painter.state

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() },
        border = if (pokemon.isFavorite) {
            BorderStroke(2.dp, Color.Blue)
        } else {
            BorderStroke(1.dp, Color.LightGray)
        }
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
                if (state is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else if (state is AsyncImagePainter.State.Error) {
                    Text(
                        text = "이미지 로딩 실패",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Image(
                    painter = painter,
                    contentDescription = pokemon.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = pokemon.name, style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (pokemon.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Toggle Favorite"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenContentPreview() {
    val fakeList = List(12) {
        PokemonModel(
            id = it,
            name = "Poke$it",
            imageUrl = buildPokemonImageUrl(25),
            isFavorite = it % 2 == 0
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(fakeList) { pokemon ->
            PokemonItem(
                pokemon = pokemon,
                onClick = {},
                onFavoriteClick = {}
            )
        }
    }
}

