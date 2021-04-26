package com.example.androiddevchallenge

import com.example.androiddevchallenge.data.model.ServiceId

inline class ScreenRoute(val route: String) {
    companion object {
        val ServicesListRoute = ScreenRoute("/list")
        val ServiceDetailBaseRoute = ScreenRoute("/details/{serviceId}")

        fun serviceDetail(id: ServiceId): String {
            return ServiceDetailBaseRoute.route.replace("{serviceId}", id.value)
        }
    }
}