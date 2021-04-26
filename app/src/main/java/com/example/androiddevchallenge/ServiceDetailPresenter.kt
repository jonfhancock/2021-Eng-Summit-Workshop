package com.example.androiddevchallenge

import com.example.androiddevchallenge.data.Repository
import com.example.androiddevchallenge.data.model.ServiceId
import com.example.androiddevchallenge.ui.model.ServiceDetailViewState
import com.example.androiddevchallenge.ui.model.toDetailPresentation
import com.example.androiddevchallenge.ui.model.toPresentation
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat

class ServiceDetailPresenter(serviceId: ServiceId) {
    private val repo = Repository.providedRepository

    private fun staffForService(serviceId: ServiceId) =
        repo.categoryForService(serviceId).flatMapConcat {
            repo.staffByCategory(it.id)
        }

    val viewState = combine(
        repo.service(serviceId),
        staffForService(serviceId)
    ) { service, staff ->
        ServiceDetailViewState.Ready(
            service.toDetailPresentation(),
            staff.toPresentation()
        ) as ServiceDetailViewState
    }.catch {
        emit(ServiceDetailViewState.NotFound)
    }
}