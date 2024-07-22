package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyconverter.R
import dev.nonoxy.currencyconverter.features.currencyconverter.domain.models.CurrencyConversionUI
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencyConverterConversionResultView(
    conversionResult: CurrencyConversionUI
) {
    Row(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "${conversionResult.baseCode} to ${conversionResult.targetCode}",
                style = CurrencyConverterTheme.typography.titleMedium,
                color = CurrencyConverterTheme.colors.primaryText
            )
            Text(
                text = stringResource(R.string.you_converted) + " ${conversionResult.baseAmount} ${conversionResult.baseCode}",
                style = CurrencyConverterTheme.typography.bodySmall,
                color = CurrencyConverterTheme.colors.secondaryText
            )
            Text(
                text = buildAnnotatedString {
                    append("1 ${conversionResult.baseCode} = ")
                    if (conversionResult.conversionRate.substringAfter('.').length > 2) {
                        append(
                            conversionResult.conversionRate.take(
                                conversionResult.conversionRate.indexOf('.') + 3
                            ) // Take number with 2 digit after dot
                        )
                        withStyle(
                            style = SpanStyle(
                                color = CurrencyConverterTheme.colors.secondaryText.copy(alpha = .6f)
                            )
                        ) {
                            append(
                                conversionResult.conversionRate.substring(
                                    conversionResult.conversionRate.indexOf('.') + 3
                                )
                            )
                        }
                    } else {
                        append(
                            conversionResult.conversionRate +
                                    if (conversionResult.conversionRate.substringAfter('.').length < 2)
                                        "0"
                                    else ""
                        )
                    }
                    append(" ${conversionResult.targetCode}")
                },
                style = CurrencyConverterTheme.typography.bodySmall,
                color = CurrencyConverterTheme.colors.secondaryText
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = buildAnnotatedString {
                if (conversionResult.convertedAmount.substringAfter('.').length > 2) {
                    append(
                        conversionResult.convertedAmount.take(
                            conversionResult.convertedAmount.indexOf('.') + 3
                        ) // Take number with 2 digit after dot
                    )
                    withStyle(
                        style = SpanStyle(
                            color = CurrencyConverterTheme.colors.primaryText.copy(alpha = .6f)
                        )
                    ) {
                        append(
                            conversionResult.convertedAmount.substring(
                                conversionResult.convertedAmount.indexOf('.') + 3
                            )
                        )
                    }
                } else {
                    append(
                        conversionResult.convertedAmount +
                                if (conversionResult.convertedAmount.substringAfter('.').length < 2)
                                    "0"
                                else ""
                    )
                }
                append(" ${conversionResult.targetCode}")
            },
            style = CurrencyConverterTheme.typography.titleMedium,
            color = CurrencyConverterTheme.colors.primaryText
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun CurrencyConverterConversionResultView_Preview() {
    CurrencyConverterTheme {
        CurrencyConverterConversionResultView(
            conversionResult = CurrencyConversionUI(
                baseCode = "USD",
                targetCode = "EUR",
                baseAmount = "100",
                convertedAmount = "84.156",
                conversionRate = "0.84156"
            )
        )
    }
}