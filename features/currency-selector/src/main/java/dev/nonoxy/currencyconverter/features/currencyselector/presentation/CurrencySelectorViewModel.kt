package dev.nonoxy.currencyconverter.features.currencyselector.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.nonoxy.currencyconverter.data.RequestResult
import dev.nonoxy.currencyconverter.features.common.base.BaseViewModel
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyFieldType
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyInfoUI
import dev.nonoxy.currencyconverter.features.currencyselector.domain.usecases.GetCurrencyResourcesInfoUseCase
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorAction
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorEvent
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencySelectorViewModel @Inject constructor(
    private val getCurrencyResourcesInfoUseCase: GetCurrencyResourcesInfoUseCase
) : BaseViewModel<CurrencySelectorViewState, CurrencySelectorAction, CurrencySelectorEvent>(
    CurrencySelectorViewState.Loading
) {
    private var searchJob: Job? = null

    init {
        obtainEvent(CurrencySelectorEvent.FetchData)
    }

    override fun obtainEvent(viewEvent: CurrencySelectorEvent) {
        when (viewEvent) {
            CurrencySelectorEvent.FetchData -> fetchCurrencyResources()

            CurrencySelectorEvent.SwapCurrency -> {
                when (val currentState = viewState) {
                    is CurrencySelectorViewState.Display -> {
                        val temp = currentState.fromCurrency
                        viewState = currentState.copy(
                            fromCurrency = currentState.targetCurrency,
                            targetCurrency = temp
                        )
                    }

                    else -> viewState = currentState
                }
            }

            is CurrencySelectorEvent.OnChangeCurrencyAmountValue -> {
                when (val currentState = viewState) {
                    is CurrencySelectorViewState.Display -> viewState = currentState.copy(
                        currencyAmount = viewEvent.newValue
                    )

                    else -> viewState = currentState
                }
            }

            is CurrencySelectorEvent.OnChangeCurrencyFieldClick -> {
                when (val currentState = viewState) {
                    is CurrencySelectorViewState.Display -> {
                        viewState =
                            currentState.copy(selectedCurrencyField = viewEvent.currencyFieldType)
                        viewAction = CurrencySelectorAction.ShowSearchCurrencyBottomModalSheet
                    }

                    else -> viewState = currentState
                }
            }

            is CurrencySelectorEvent.OnChangeCurrencySearchValue -> updateSearchText(viewEvent.newValue)

            is CurrencySelectorEvent.OnSelectCurrency -> {
                when (val currentState = viewState) {
                    is CurrencySelectorViewState.Display -> {
                        when (currentState.selectedCurrencyField) {
                            CurrencyFieldType.CurrencyBase -> {
                                viewState = currentState.copy(fromCurrency = viewEvent.currencyInfo)
                            }

                            CurrencyFieldType.CurrencyTarget -> {
                                viewState =
                                    currentState.copy(targetCurrency = viewEvent.currencyInfo)
                            }
                        }
                    }

                    else -> viewState = currentState
                }
            }

            is CurrencySelectorEvent.OnConvertCurrencyButtonClick -> {
                when (val currentState = viewState) {
                    is CurrencySelectorViewState.Display -> {
                        try {
                            currentState.currencyAmount.toFloat() // check input is correctly
                            viewAction = CurrencySelectorAction.NavigateToConversionResult
                        } catch (e: NumberFormatException) {
                            viewAction = CurrencySelectorAction.ShowCurrencyAmountInputError
                        }
                    }
                    else -> viewState = currentState
                }
            }
        }
    }

    private fun fetchCurrencyResources() {
        viewModelScope.launch {
            getCurrencyResourcesInfoUseCase.execute().collect { result ->
                when (result) {
                    is RequestResult.Error -> viewState = CurrencySelectorViewState.Error
                    is RequestResult.InProgress -> viewState = CurrencySelectorViewState.Loading
                    is RequestResult.Success -> {
                        val defaultFromCurrency = async(Dispatchers.Default) {
                            result.data.find { it.currencyCode == "RUB" } ?: CurrencyInfoUI(
                                currencyCode = "RUB",
                                currencyName = "Russian Ruble",
                                currencySymbol = "₽",
                                countryCode = "ru"
                            )
                        }
                        val defaultTargetCurrency = async(Dispatchers.Default) {
                            result.data.find { it.currencyCode == "USD" } ?: CurrencyInfoUI(
                                currencyCode = "USD",
                                currencyName = "United States Dollar",
                                currencySymbol = "$",
                                countryCode = "us"
                            )
                        }

                        viewState = CurrencySelectorViewState.Display(
                            currencies = result.data,
                            filteredCurrencies = result.data,
                            fromCurrency = defaultFromCurrency.await(),
                            targetCurrency = defaultTargetCurrency.await()
                        )
                    }
                }
            }
        }
    }

    private fun updateSearchText(newValue: String) {
        when (val currentState = viewState) {
            is CurrencySelectorViewState.Display -> {
                viewState = currentState.copy(currencySearchTextValue = newValue)
                debounceSearch()
            }

            else -> viewState = currentState
        }
    }

    private fun debounceSearch() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            when (val currentState = viewState) {
                is CurrencySelectorViewState.Display -> {
                    val filteredCurrencies = withContext(Dispatchers.Default) {
                        currentState.currencies.filter { currency ->
                            currency.currencyCode.contains(
                                currentState.currencySearchTextValue,
                                ignoreCase = true
                            )
                        }
                    }
                    viewState = currentState.copy(filteredCurrencies = filteredCurrencies)
                }

                else -> viewState = currentState
            }
        }
    }
}