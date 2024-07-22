package dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyInfoUI
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencySelectorBaseCurrencyView(
    modifier: Modifier = Modifier,
    selectedCurrency: CurrencyInfoUI,
) {
    CurrencySelectorFTextFieldView(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                text = buildAnnotatedString {
                    append("${selectedCurrency.currencyCode} – ")
                    withStyle(
                        style = SpanStyle(
                            CurrencyConverterTheme.colors.secondaryText.copy(
                                alpha = .7f
                            )
                        )
                    ) {
                        append(selectedCurrency.currencyName)
                    }
                }
            )
        },
        leadingIcon = {
            AsyncImage(
                model = "https://flagcdn.com/w40/${selectedCurrency.countryCode}.png",
                contentDescription = null,
                imageLoader = LocalContext.current.imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 25.dp, 21.dp)
                    .border(width = Dp.Hairline, color = CurrencyConverterTheme.colors.secondaryText)
            )
        },
        readOnly = true,
        enabled = false,
        modifier = modifier
    )
}