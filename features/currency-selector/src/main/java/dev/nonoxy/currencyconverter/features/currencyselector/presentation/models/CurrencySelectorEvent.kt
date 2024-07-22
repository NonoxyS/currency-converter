package dev.nonoxy.currencyconverter.features.currencyselector.presentation.models

import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyFieldType
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyInfoUI

sealed class CurrencySelectorEvent {
    data object FetchData : CurrencySelectorEvent()
    data object SwapCurrency : CurrencySelectorEvent()
    class OnChangeCurrencyFieldClick(val currencyFieldType: CurrencyFieldType): CurrencySelectorEvent()
    class OnSelectCurrency(val currencyInfo: CurrencyInfoUI) : CurrencySelectorEvent()
    class OnChangeCurrencyAmountValue(val newValue: String) : CurrencySelectorEvent()
    class OnChangeCurrencySearchValue(val newValue: String) : CurrencySelectorEvent()
    data object OnConvertCurrencyButtonClick : CurrencySelectorEvent()
}