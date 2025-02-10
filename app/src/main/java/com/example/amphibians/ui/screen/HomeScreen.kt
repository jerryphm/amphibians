package com.example.amphibians.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.amphibians.R
import com.example.amphibians.data.Amphibian

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AmphibiansViewModel = viewModel(factory = AmphibiansViewModel.Factory),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
            )
        },
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (uiState) {
                is AmphibiansUiState.Loading -> Loading()
                is AmphibiansUiState.Error -> Error()
                is AmphibiansUiState.Success -> ListSpecies((uiState as AmphibiansUiState.Success).amphibians)
            }

        }

    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Text("Loading...")
}

@Composable
fun Error(modifier: Modifier = Modifier) {
    Text("Error while loading content,\nplease try again later.", textAlign = TextAlign.Center)
}

@Composable
fun ListSpecies(
    listSpecies: List<Amphibian>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(listSpecies) {
            Species(it)
        }
    }
}

@Composable
fun Species(
    species: Amphibian,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0XFFDEE5D8))
    ) {
        Text(
            text = "${species.name} (${species.type})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
        )
        Log.d("jerrytest", "Species: ${species.imgSrc}")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(species.imgSrc)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.placeholder_image),
            contentDescription = "species photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.77f)
                .background(Color.LightGray)
        )
        Text(
            text = species.description,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}