package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models

import dev.nonoxy.currencyconverter.features.currencyconverter.domain.models.CurrencyConversionUI

sealed class CurrencyConverterViewState {
    data object Loading : CurrencyConverterViewState()
    class Error(val conversion: CurrencyConversionUI) : CurrencyConverterViewState()
    data class Display(val conversion: CurrencyConversionUI) : CurrencyConverterViewState()
}