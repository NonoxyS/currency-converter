package dev.nonoxy.currencyconverter.features.currencyselector.presentation.models

import androidx.compose.runtime.Stable
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyFieldType
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyInfoUI

sealed class CurrencySelectorViewState {
    data object Loading : CurrencySelectorViewState()
    data object Error : CurrencySelectorViewState()
    @Stable
    data class Display(
        val currencies: List<CurrencyInfoUI>,
        val filteredCurrencies: List<CurrencyInfoUI>,
        val fromCurrency: CurrencyInfoUI,
        val targetCurrency: CurrencyInfoUI,
        val selectedCurrencyField: CurrencyFieldType = CurrencyFieldType.CurrencyBase,
        val currencyAmount: String = "1.00",
        val currencySearchTextValue: String = "",
    ) : CurrencySelectorViewState()
}