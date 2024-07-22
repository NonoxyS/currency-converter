package dev.nonoxy.restaurant_reviews.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.nonoxy.restaurant_reviews.common.LocalNavHost
import dev.nonoxy.restaurant_reviews.restaurantdetail.presentation.RestaurantDetailViewModel
import dev.nonoxy.restaurant_reviews.restaurantdetail.presentation.models.RestaurantDetailEvent
import dev.nonoxy.restaurant_reviews.restaurantdetail.presentation.ui.RestaurantDetailScreen
import dev.nonoxy.restaurant_reviews.restaurants.presentation.ui.RestaurantsScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    CompositionLocalProvider(
        LocalNavHost provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Restaurants
        ) {
            composable<Screen.Restaurants> {
                RestaurantsScreen { restaurantId ->
                    navController.navigate(
                        Screen.RestaurantDetail(restaurantId)
                    ) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }

            composable<Screen.RestaurantDetail> { backStackEntry ->
                val restaurantDetailViewModel: RestaurantDetailViewModel = hiltViewModel()
                val args = backStackEntry.toRoute<Screen.RestaurantDetail>()
                LaunchedEffect(key1 = args) {
                    restaurantDetailViewModel.obtainEvent(RestaurantDetailEvent.FetchData(args.restaurantId))
                }
                RestaurantDetailScreen(restaurantDetailViewModel)
            }
        }
    }
}