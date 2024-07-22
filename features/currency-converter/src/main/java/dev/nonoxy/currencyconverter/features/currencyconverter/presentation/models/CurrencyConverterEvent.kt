package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models

sealed class CurrencyConverterEvent {
    class FetchData(
        val baseCurrencyCode: String,
        val targetCurrencyCode: String,
        val amount: String,
    ) : CurrencyConverterEvent()
    data object NavigateToCurrencySelectorButtonClicked : CurrencyConverterEvent()
}