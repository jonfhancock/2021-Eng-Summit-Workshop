package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.*

class Repository {

    fun staffFlow(): Flow<List<Staff>> = flow {
        delay(200)
        emit(StaffMembers.allStaff)
    }

    fun servicesFlow(): Flow<List<Service>> = flow {
        delay(200)
        emit(Services.allServices)
    }

    fun categoriesFlow(): Flow<List<ServiceCategory>> = flow {
        delay(200)
        emit(Categories.allCategories)
    }

    fun service(serviceId: ServiceId) =
        servicesFlow().map { services ->
            services.firstOrNull { it.id == serviceId } ?: error("Service $serviceId not found")
        }

    fun staffByCategory(categoryId: ServiceCategoryId) =
        staffFlow().map { staffMembers ->
            staffMembers.filter {
                it.serviceCategories.contains(categoryId)
            }
        }

    fun categoryForService(serviceId: ServiceId) =
        categoriesFlow().map { categories ->
            categories.first {
                it.serviceIds.contains(serviceId)
            }
        }

    companion object {
        val providedRepository: Repository by lazy { Repository() }
    }
}


object Services {
    val therapeuticMassage = Service(
        ServiceId("therapeutic-massage"),
        "Therapeutic Massage",
        R.drawable.image_therapeutic_massage,
        listOf(
            PriceOption(60, Currency.getInstance("USD"), 30),
            PriceOption(90, Currency.getInstance("USD"), 60),
            PriceOption(110, Currency.getInstance("USD"), 90)
        )
    )

    val relaxationMassage = Service(
        ServiceId("relaxation-massage"),
        "Relaxation Massage",
        R.drawable.image_relaxing_massage,
        listOf(
            PriceOption(50, Currency.getInstance("USD"), 30),
            PriceOption(70, Currency.getInstance("USD"), 60),
            PriceOption(100, Currency.getInstance("USD"), 90)
        )
    )

    val browWax = Service(
        ServiceId("eyebrow-wax"),
        "Eyebrow Wax",
        R.drawable.image_eyebrow_wax,
        listOf(
            PriceOption(15, Currency.getInstance("USD"), 15)
        )
    )

    val massageServices = listOf(therapeuticMassage, relaxationMassage)
    val waxServices = listOf(browWax)

    val allServices = massageServices + waxServices
}

object Categories {
    val massage = ServiceCategory(
        ServiceCategoryId("massage"),
        "Massage",
        Services.massageServices.map { it.id }
    )
    val wax = ServiceCategory(
        ServiceCategoryId("wax"),
        "Waxing",
        Services.waxServices.map { it.id }
    )
    val allCategories = listOf(massage, wax)
}

object StaffMembers {
    val staff1 = Staff(
        StaffId("1"),
        "Ijeoma M.",
        R.drawable.avatar_ijeoma,
        listOf(Categories.massage.id)
    )
    val staff2 = Staff(
        StaffId("2"),
        "Martín E.",
        R.drawable.avatar_martin,
        listOf(Categories.massage.id, Categories.wax.id)
    )

    val allStaff = listOf(staff1, staff2)
}
