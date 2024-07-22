package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models

sealed class CurrencyConverterAction {
    data object NavigateBackToSelectCurrency: CurrencyConverterAction()
}