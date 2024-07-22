package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyconverter.R
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencyConverterTopBarView() {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.conversion_result),
            style = CurrencyConverterTheme.typography.titleLarge,
            color = CurrencyConverterTheme.colors.primaryText
        )
    }
}