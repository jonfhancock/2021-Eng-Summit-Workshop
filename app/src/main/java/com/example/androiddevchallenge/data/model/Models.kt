package com.example.androiddevchallenge.data.model

import androidx.annotation.DrawableRes
import java.util.*

inline class ServiceId(val value: String)
inline class StaffId(val value: String)
inline class ServiceCategoryId(val value: String)


data class PriceOption(
    val price: Int,
    val currency: Currency,
    val durationMinutes: Long
)

data class ServiceCategory(
    val id: ServiceCategoryId,
    val name: String,
    val serviceIds: List<ServiceId>
)

data class Staff(
    val id: StaffId,
    val name: String,
    @DrawableRes val avatarResourceId: Int,
    val serviceCategories: List<ServiceCategoryId>
)

data class Service(
    val id: ServiceId,
    val name: String,
    @DrawableRes val imageResourceId: Int,
    val priceOptions: List<PriceOption>
)
