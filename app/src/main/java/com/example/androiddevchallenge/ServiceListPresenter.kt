package com.example.androiddevchallenge

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.toMutableStateMap
import com.example.androiddevchallenge.data.Categories
import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.data.Services
import com.example.androiddevchallenge.data.model.Service
import com.example.androiddevchallenge.data.model.ServiceCategory
import com.example.androiddevchallenge.ui.model.ServiceCategoryState
import com.example.androiddevchallenge.ui.model.ServiceListViewState
import com.example.androiddevchallenge.ui.model.toListViewPresentation

class ServiceListPresenter {
    private val repo = Repository.providedRepository

    private val categories = Categories.allCategories.map { it to true }.toMutableStateMap()

    val viewState = derivedStateOf<ServiceListViewState> {
        val services = Services.allServices

        ServiceListViewState.Ready(
            services.filterBySelectedCategories(categories.toList()).toListViewPresentation(),
            categories.toList()
        )

    }

    private fun List<Service>.filterBySelectedCategories(categories: List<ServiceCategoryState>) =
        filter { service ->
            categories.any { (category, selected) ->
                selected && category.serviceIds.contains(service.id)
            }
        }

    fun updateCategorySelection(category: ServiceCategory, checked: Boolean) {
        categories.put(category, checked)
    }
}