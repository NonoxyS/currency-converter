package dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dev.nonoxy.currencyconverter.features.currencyselector.R
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.ConversionCurrencyInfo
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.CurrencySelectorViewModel
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorAction
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorEvent
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorViewState
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views.CurrencySelectorErrorView
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views.CurrencySelectorLoadingView
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views.CurrencySelectorModalBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectorScreen(
    currencySelectorViewModel: CurrencySelectorViewModel = hiltViewModel(),
    navigateToConversionResult: (ConversionCurrencyInfo) -> Unit
) {
    val viewState by currencySelectorViewModel.viewStates().collectAsState()
    val viewAction by currencySelectorViewModel.viewActions().collectAsState(initial = null)

    when (val currentState = viewState) {
        CurrencySelectorViewState.Error -> {
            CurrencySelectorErrorView(
                onRefreshClick = { currencySelectorViewModel.obtainEvent(CurrencySelectorEvent.FetchData) }
            )
        }

        CurrencySelectorViewState.Loading -> CurrencySelectorLoadingView()

        is CurrencySelectorViewState.Display -> {
            CurrencySelectorView(
                viewState = currentState,
                eventHandler = currencySelectorViewModel::obtainEvent
            )
        }
    }

    when (val currentAction = viewAction) {
        is CurrencySelectorAction.ShowSearchCurrencyBottomModalSheet -> {
            when (val currentState = viewState) {
                is CurrencySelectorViewState.Display -> {
                    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                    val coroutineScope = rememberCoroutineScope()
                    CurrencySelectorModalBottomSheet(
                        viewState = currentState,
                        sheetState = sheetState,
                        onDismiss = {
                            coroutineScope.launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    currencySelectorViewModel.obtainEvent(
                                        CurrencySelectorEvent.OnChangeCurrencySearchValue(
                                            ""
                                        )
                                    )
                                    currencySelectorViewModel.clearAction()
                                }
                        },
                        eventHandler = currencySelectorViewModel::obtainEvent
                    )
                }

                else -> currencySelectorViewModel.clearAction()
            }
        }

        CurrencySelectorAction.ShowCurrencyAmountInputError -> {
            Toast.makeText(
                LocalContext.current,
                stringResource(R.string.incorrect_currency_input),
                Toast.LENGTH_SHORT
            ).show()
            currencySelectorViewModel.clearAction()
        }

        CurrencySelectorAction.NavigateToConversionResult -> {
            when (val currentState = viewState) {
                is CurrencySelectorViewState.Display -> {
                    navigateToConversionResult(
                        ConversionCurrencyInfo(
                            baseCurrencyCode = currentState.fromCurrency.currencyCode,
                            targetCurrencyCode = currentState.targetCurrency.currencyCode,
                            amount = currentState.currencyAmount
                        )
                    )
                }

                else -> Unit
            }
            currencySelectorViewModel.clearAction()
        }

        null -> Unit
    }
}