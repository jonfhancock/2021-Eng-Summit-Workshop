package com.example.androiddevchallenge.ui.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.AnnotatedString
import com.example.androiddevchallenge.data.model.ServiceCategory
import com.example.androiddevchallenge.data.model.ServiceId
import com.example.androiddevchallenge.data.model.StaffId

inline class PricePresentation(val value: String)
inline class DurationPresentation(val value: String)

typealias ServiceCategoryState = Pair<ServiceCategory, Boolean>

val ServiceCategoryState.category get() = first
val ServiceCategoryState.selected get() = second

fun DurationPresentation(min: DurationPresentation, max: DurationPresentation) =
    DurationPresentation("${min.value} - ${max.value}")

fun PricePresentation(min: PricePresentation, max: PricePresentation) =
    PricePresentation("${min.value} - ${max.value}")


sealed class ServiceListViewState(val title: String) {
    data class Ready(
        val services: List<ServiceListItem>,
        val categories: List<ServiceCategoryState>
    ) : ServiceListViewState("Salon Services")

    object Loading : ServiceListViewState("Salon Services")
}


sealed class ServiceDetailViewState(val title: String) {
    data class Ready(
        val service: ServiceDetailItem,
        val staff: List<StaffListItem>
    ) : ServiceDetailViewState(service.name)

    object NotFound : ServiceDetailViewState("Not Found")
    object Loading : ServiceDetailViewState("")
}

data class ServiceListItem(
    val id: ServiceId,
    val name: String,
    @DrawableRes val imageResourceId: Int,
    val priceRange: PricePresentation,
    val durationRange: DurationPresentation
)

data class ServiceDetailItem(
    val id: ServiceId,
    val name: String,
    @DrawableRes val imageResourceId: Int,
    val prices: List<AnnotatedString>
)

data class StaffListItem(
    val id: StaffId,
    val name: String,
    @DrawableRes val imageResourceId: Int,
)

