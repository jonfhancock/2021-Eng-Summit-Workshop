/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.androiddevchallenge.ScreenRoute.Companion.ServiceDetailBaseRoute
import com.example.androiddevchallenge.ScreenRoute.Companion.ServicesListRoute
import com.example.androiddevchallenge.ScreenRoute.Companion.serviceDetail
import com.example.androiddevchallenge.data.Categories
import com.example.androiddevchallenge.data.Services
import com.example.androiddevchallenge.data.StaffMembers
import com.example.androiddevchallenge.data.model.ServiceId
import com.example.androiddevchallenge.ui.model.*
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun ServiceListScreen(
    navController: NavController,
    presenter: ServiceListPresenter
) {
    val viewState by presenter.viewState
    when (viewState) {
        ServiceListViewState.Loading -> ServicesLoading(viewState as ServiceListViewState.Loading)
        is ServiceListViewState.Ready -> ServiceList(
            viewState = viewState as ServiceListViewState.Ready,
            updateCategorySelection = {
                presenter.updateCategorySelection(it.category, it.selected)
            },
            onServiceSelected = {
                navController.navigate(serviceDetail(it))
            })
    }

}

// Start building your app here!
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ServicesListRoute.route) {
        composable(ServicesListRoute.route) {
            ServiceListScreen(navController, remember { ServiceListPresenter() })
        }
        composable(
            ServiceDetailBaseRoute.route,
            arguments = listOf(navArgument("serviceId") {})
        ) { backstackEntry ->
            val serviceId = ServiceId(backstackEntry.arguments?.getString("serviceId")!!)
            ServiceDetailScreen(
                navController,
                remember(serviceId) { ServiceDetailPresenter(serviceId) }
            )
        }
    }


}

@Composable
fun ServiceDetailScreen(navController: NavController, presenter: ServiceDetailPresenter) {
    val viewState by presenter.viewState.collectAsState(initial = ServiceDetailViewState.Loading)
    ServiceDetail(
        viewState,
        navigateUp = { navController.navigateUp() }
    )
}

@Preview()
@Composable
fun LightPreviewList() {
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

@Preview()
@Composable
fun LightPreviewDetail() {
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