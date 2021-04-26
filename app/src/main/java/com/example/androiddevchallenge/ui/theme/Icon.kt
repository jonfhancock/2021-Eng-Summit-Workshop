package com.example.androiddevchallenge.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HomeNavIcon() {
    Icon(
        Icons.Default.Spa,
        contentDescription = null,
        modifier = Modifier.padding(start = 8.dp)
    )
}

@Composable
fun FilterActionIcon(bottomSheetState: BottomSheetState) {
    val coroutineScope = rememberCoroutineScope()
    Icon(
        Icons.Default.FilterList,
        contentDescription = "Filter",
        tint = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .size(36.dp)
            .clickable {
                coroutineScope.launch {
                    bottomSheetState.expand()
                }
            }
    )
}

@Composable
fun BackArrowNavIcon(navigateUp: () -> Unit) {
    Icon(
        Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier.clickable { navigateUp() }
    )
}