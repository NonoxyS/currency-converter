package dev.nonoxy.currencyconverter.features.currencyselector.presentation.models

sealed class CurrencySelectorAction {
    data object ShowSearchCurrencyBottomModalSheet : CurrencySelectorAction()
    data object ShowCurrencyAmountInputError : CurrencySelectorAction()
    data object NavigateToConversionResult : CurrencySelectorAction()
}