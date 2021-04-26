package com.example.androiddevchallenge.ui.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.androiddevchallenge.data.model.PriceOption
import com.example.androiddevchallenge.data.model.Service
import com.example.androiddevchallenge.data.model.Staff
import java.text.NumberFormat


fun Service.toDetailPresentation() = ServiceDetailItem(
    id,
    name,
    imageResourceId,
    priceOptions.toListPresentation()
)

private fun List<PriceOption>.toListPresentation(): List<AnnotatedString> =
    this.sortedBy { it.durationMinutes }
        .map {
            buildAnnotatedString {
                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(it.durationPresentation.value)
                }
                append(" | ")
                append(it.pricePresentation.value)
            }
        }

fun Service.toListViewPresentation() = ServiceListItem(
    id,
    name,
    imageResourceId,
    priceRange,
    durationRange
)

fun List<Service>.toListViewPresentation() = map {
    it.toListViewPresentation()
}

val Service.priceRange: PricePresentation
    get() {
        val prices = priceOptions
            .sortedBy { it.price }
            .map { it.pricePresentation }
        return when {
            prices.isEmpty() -> PricePresentation("Free!")
            prices.size == 1 -> prices.first()
            else -> PricePresentation(prices.first(), prices.last())
        }
    }

val Service.durationRange: DurationPresentation
    get() {
        val durations = priceOptions
            .sortedBy { it.durationMinutes }
            .map { it.durationPresentation }
        return when {
            durations.isEmpty() -> DurationPresentation("")
            durations.size == 1 -> durations.first()
            else -> DurationPresentation(durations.first(), durations.last())
        }
    }

fun Staff.toPresentation() = StaffListItem(id, name, avatarResourceId)
fun List<Staff>.toPresentation() = map { it.toPresentation() }

val PriceOption.durationPresentation: DurationPresentation
    get() {
        val hours = durationMinutes / 60
        val minutes = durationMinutes % 60
        return when {
            hours > 0 && minutes > 0 -> DurationPresentation("${hours}h ${minutes}m")
            hours > 0 && minutes <= 0 -> DurationPresentation("${hours}h")
            else -> DurationPresentation("${minutes}m")
        }
    }

val PriceOption.pricePresentation: PricePresentation
    get() = PricePresentation(
        NumberFormat.getCurrencyInstance()
            .also { formatter -> formatter.currency = currency }
            .format(price)
    )
