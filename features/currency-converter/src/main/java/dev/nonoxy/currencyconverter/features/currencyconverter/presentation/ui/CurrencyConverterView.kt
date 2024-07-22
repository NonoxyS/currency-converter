package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyconverter.R
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterEvent
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.models.CurrencyConverterViewState
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views.CurrencyConverterConversionResultView
import dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views.CurrencyConverterTopBarView
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencyConverterView(
    viewState: CurrencyConverterViewState.Display,
    eventHandler: (CurrencyConverterEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        CurrencyConverterTopBarView()
        CurrencyConverterConversionResultView(conversionResult = viewState.conversion)

        TextButton(
            onClick = { eventHandler(CurrencyConverterEvent.NavigateToCurrencySelectorButtonClicked) },
            colors = ButtonDefaults.buttonColors(
                containerColor = CurrencyConverterTheme.colors.primaryContainer,
                contentColor = CurrencyConverterTheme.colors.primaryText
            ),
            contentPadding = ButtonDefaults.TextButtonWithIconContentPadding
        ) {
            Text(
                text = stringResource(R.string.new_conversion),
                style = CurrencyConverterTheme.typography.titleSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}