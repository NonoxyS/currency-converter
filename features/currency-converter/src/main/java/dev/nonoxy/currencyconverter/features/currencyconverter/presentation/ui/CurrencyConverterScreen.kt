package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.CurrencyConverterViewModel
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterAction
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterEvent
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterViewState
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views.CurrencyConverterErrorView
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views.CurrencyConverterLoadingView

@Composable
fun CurrencyConverterScreen(
    currencyConverterViewModel: CurrencyConverterViewModel = hiltViewModel(),
    navigateToSelectCurrency: () -> Unit
) {
    val viewState by currencyConverterViewModel.viewStates().collectAsState()
    val viewAction by currencyConverterViewModel.viewActions().collectAsState(initial = null)

    when (val currentState = viewState) {
        CurrencyConverterViewState.Loading -> CurrencyConverterLoadingView()
        is CurrencyConverterViewState.Error -> CurrencyConverterErrorView(
            onRefreshClick = {
                currencyConverterViewModel.obtainEvent(
                    CurrencyConverterEvent.FetchData(
                        baseCurrencyCode = currentState.conversion.baseCode,
                        targetCurrencyCode = currentState.conversion.targetCode,
                        amount = currentState.conversion.baseAmount
                    )
                )
            }
        )

        is CurrencyConverterViewState.Display -> CurrencyConverterView(
            viewState = currentState,
            eventHandler = currencyConverterViewModel::obtainEvent
        )
    }

    when (viewAction) {
        CurrencyConverterAction.NavigateBackToSelectCurrency -> {
            navigateToSelectCurrency()
            currencyConverterViewModel.clearAction()
        }

        null -> Unit
    }
}