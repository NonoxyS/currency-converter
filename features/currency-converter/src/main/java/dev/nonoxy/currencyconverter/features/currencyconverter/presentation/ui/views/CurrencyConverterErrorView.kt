package dev.nonoxy.currencyconverter.features.currencyconverter.presentation.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyconverter.R
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencyConverterErrorView(
    onRefreshClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(42.dp)
        ) {
            Text(
                text = stringResource(R.string.error_happen),
                style = CurrencyConverterTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            TextButton(
                onClick = onRefreshClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CurrencyConverterTheme.colors.primaryButton,
                    contentColor = CurrencyConverterTheme.colors.onPrimaryButton
                ),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.refresh),
                    style = CurrencyConverterTheme.typography.bodyMedium,
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}