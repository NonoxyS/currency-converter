package dev.nonoxy.currencyconverter.features.currencyconverter.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.features.common.base.BaseViewModel
import dev.nonoxy.currencyconverter.features.currencyconverter.domain.models.CurrencyConversionUI
import dev.nonoxy.currencyconverter.features.currencyconverter.domain.usecases.ConvertCurrencyUseCase
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterAction
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterEvent
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : BaseViewModel<CurrencyConverterViewState, CurrencyConverterAction, CurrencyConverterEvent>(
    initialState = CurrencyConverterViewState.Loading
) {
    override fun obtainEvent(viewEvent: CurrencyConverterEvent) {
        when (viewEvent) {
            is CurrencyConverterEvent.FetchData -> fetchData(
                baseCode = viewEvent.baseCurrencyCode,
                targetCode = viewEvent.targetCurrencyCode,
                amount = viewEvent.amount
            )

            CurrencyConverterEvent.NavigateToCurrencySelectorButtonClicked -> {
                viewAction = CurrencyConverterAction.NavigateBackToSelectCurrency
            }
        }
    }

    private fun fetchData(baseCode: String, targetCode: String, amount: String) {
        viewModelScope.launch {
            convertCurrencyUseCase.execute(
                baseCode = baseCode,
                targetCode = targetCode,
                amount = amount
            ).collect { result ->
                when (result) {
                    is RequestResult.InProgress -> viewState = CurrencyConverterViewState.Loading
                    is RequestResult.Error -> viewState = CurrencyConverterViewState.Error(
                        conversion = CurrencyConversionUI(
                            baseCode = baseCode,
                            targetCode = targetCode,
                            baseAmount = amount,
                            convertedAmount = "",
                            conversionRate = ""
                        )
                    )

                    is RequestResult.Success -> {
                        viewState = CurrencyConverterViewState.Display(
                            conversion = CurrencyConversionUI(
                                baseCode = result.data.baseCode,
                                targetCode = result.data.targetCode,
                                baseAmount = result.data.baseAmount,
                                convertedAmount = result.data.convertedAmount,
                                conversionRate = result.data.conversionRate
                            )
                        )
                    }
                }
            }
        }
    }
}