package com.example.androiddevchallenge

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Services
import com.example.androiddevchallenge.data.StaffMembers
import com.example.androiddevchallenge.ui.model.ServiceDetailViewState
import com.example.androiddevchallenge.ui.model.StaffListItem
import com.example.androiddevchallenge.ui.model.toDetailPresentation
import com.example.androiddevchallenge.ui.model.toPresentation
import com.example.androiddevchallenge.ui.theme.BackArrowNavIcon
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun ServiceDetail(
    viewState: ServiceDetailViewState,
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(viewState.title) },
                navigationIcon = { BackArrowNavIcon(navigateUp) }
            )
        }
    ) {
        when (viewState) {
            is ServiceDetailViewState.Ready -> ServiceReady(viewState)
            ServiceDetailViewState.NotFound -> ServiceNotFound()
            ServiceDetailViewState.Loading -> ServiceLoading()
        }
    }
}

@Composable
private fun ServiceReady(viewState: ServiceDetailViewState.Ready) {
    val service = viewState.service
    val availableStaff = viewState.staff
    Image(
        painter = painterResource(service.imageResourceId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
    )
    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(Modifier.height(180.dp))
        PricingOptionsCard(
            service.prices,
            Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.size(8.dp))
        StaffListCard(
            availableStaff,
            Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ServiceLoading() {
    Box(
        Modifier
            .fillMaxSize()
            .background(colors.surface),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ServiceNotFound() {
    Box(
        Modifier
            .fillMaxSize()
            .background(colors.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "We couldn't find that service.",
            color = colors.onSurface
        )
    }
}

@Composable
private fun StaffListCard(availableStaff: List<StaffListItem>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.verticalGradient(
                0f to colors.secondary,
                1f to colors.secondaryVariant.copy(alpha = .5f)
            )
        ),
        contentColor = colors.onSecondary,
    ) {
        Column {
            Text(
                text = "Available Staff",
                style = typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.secondary)
                    .padding(8.dp)
            )
            Spacer(Modifier.size(4.dp))
            availableStaff.forEachIndexed { index, staff ->
                val backgroundColor = when {
                    index % 2 == 0 -> colors.secondary
                    else -> colors.secondaryVariant
                }
                Card(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(percent = 50),
                    backgroundColor = backgroundColor,
                    contentColor = colors.onSecondary
                ) {
                    Row(
                        Modifier.padding(2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(staff.imageResourceId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )

                        Text(
                            staff.name,
                            color = colors.onPrimary,
                            style = typography.h6,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.size(8.dp))
                    }
                }

            }
            Spacer(Modifier.size(4.dp))
        }
    }
}

@Composable
private fun PricingOptionsCard(prices: List<AnnotatedString>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        backgroundColor = colors.primaryVariant,
        contentColor = colors.onPrimary,
        elevation = 2.dp
    ) {
        Column {
            Text(
                "Services",
                style = typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primary)
                    .padding(8.dp)
            )
            Spacer(Modifier.size(8.dp))
            prices.forEach { priceOption ->
                Text(
                    text = priceOption,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                Spacer(Modifier.size(4.dp))
            }
            Spacer(Modifier.size(4.dp))
        }
    }
}


@Preview()
@Composable
private fun Preview() {
    MyTheme {
        ServiceDetail(
            viewState = ServiceDetailViewState.Ready(
                Services.therapeuticMassage.toDetailPresentation(),
                StaffMembers.allStaff.toPresentation()
            ),
            navigateUp = { }
        )
    }
}