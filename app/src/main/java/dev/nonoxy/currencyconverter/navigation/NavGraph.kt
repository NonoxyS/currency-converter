package dev.nonoxy.currencyconverter.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.CurrencyConverterViewModel
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterEvent
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.CurrencyConverterScreen
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.CurrencySelectorScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.CurrencySelector
    ) {
        composable<Screen.CurrencySelector>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            CurrencySelectorScreen { currencyConversionInfo ->
                navController.navigate(
                    Screen.CurrencyConversionResult(
                        baseCurrencyCode = currencyConversionInfo.baseCurrencyCode,
                        targetCurrencyCode = currencyConversionInfo.targetCurrencyCode,
                        amount = currencyConversionInfo.amount
                    )
                ) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        composable<Screen.CurrencyConversionResult>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 300 },
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { backStackEntry ->
            val currencyConverterViewModel: CurrencyConverterViewModel = hiltViewModel()
            val args = backStackEntry.toRoute<Screen.CurrencyConversionResult>()
            LaunchedEffect(key1 = args) {
                currencyConverterViewModel.obtainEvent(
                    CurrencyConverterEvent.FetchData(
                        baseCurrencyCode = args.baseCurrencyCode,
                        targetCurrencyCode = args.targetCurrencyCode,
                        amount = args.amount
                    )
                )
            }
            CurrencyConverterScreen(currencyConverterViewModel) {
                navController.navigate(Screen.CurrencySelector) {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}