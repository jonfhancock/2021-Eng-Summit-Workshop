package com.example.androiddevchallenge

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Categories
import com.example.androiddevchallenge.data.Services
import com.example.androiddevchallenge.data.model.ServiceId
import com.example.androiddevchallenge.ui.model.*
import com.example.androiddevchallenge.ui.theme.*

@Composable
fun ServicesLoading(
    viewState: ServiceListViewState.Loading
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewState.title) },
                navigationIcon = { HomeNavIcon() }
            )
        }
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(colors.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ServiceList(
    viewState: ServiceListViewState.Ready,
    updateCategorySelection: (ServiceCategoryState) -> Unit,
    onServiceSelected: (ServiceId) -> Unit
) {
    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState),
        sheetPeekHeight = 0.dp,
        sheetShape = bottomSheetShape,
        sheetElevation = 8.dp,
        sheetBackgroundColor = colors.secondaryVariant,
        sheetContent = { CategoriesBottomSheet(updateCategorySelection, viewState.categories) },
        topBar = {
            TopAppBar(
                title = { Text(viewState.title) },
                navigationIcon = { HomeNavIcon() },
                actions = { FilterActionIcon(bottomSheetState) }
            )
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            cells = GridCells.Adaptive(minSize = 180.dp)
        ) {
            items(viewState.services) { service ->
                ServiceItemCard(service, onServiceSelected)
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CategoriesBottomSheet(
    updateCategorySelection: (ServiceCategoryState) -> Unit,
    categories: List<ServiceCategoryState>
) {
    for (category in categories) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .toggleable(
                    category.selected,
                    onValueChange = { checked ->
                        updateCategorySelection(category.category to checked)
                    }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = category.selected,
                modifier = Modifier
                    .size(48.dp)
                    .padding(16.dp),
                onCheckedChange = null,
            )
            Text(
                category.category.name,
                Modifier.padding(8.dp),
                color = colors.onSurface
            )
        }
    }
}

@Composable
private fun ServiceItemCard(
    service: ServiceListItem,
    onServiceSelected: (service: ServiceId) -> Unit,
) {
    Card(
        Modifier
            .padding(4.dp)
            .clickable { onServiceSelected(service.id) },
        elevation = 2.dp,
        border = BorderStroke(1.dp, colors.primaryVariant),
        backgroundColor = colors.primaryVariant,
        contentColor = colors.onPrimary
    ) {
        Image(
            painterResource(id = service.imageResourceId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Column {
            Spacer(Modifier.height(140.dp))
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = shapes.small,
                color = colors.primarySurface,
                elevation = 4.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeightIn(48.dp)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = service.name,
                        style = typography.h6,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            Spacer(Modifier.size(4.dp))
            Text(
                text = service.durationRange.value,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = service.priceRange.value,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(Modifier.size(8.dp))

        }

    }
}


@Preview()
@Composable
private fun Preview() {
    MyTheme {
        ServiceList(
            viewState = ServiceListViewState.Ready(
                Services.allServices.toListViewPresentation(),
                Categories.allCategories.map { it to true }
            ),
            updateCategorySelection = { },
            onServiceSelected = {}
        )
    }
}