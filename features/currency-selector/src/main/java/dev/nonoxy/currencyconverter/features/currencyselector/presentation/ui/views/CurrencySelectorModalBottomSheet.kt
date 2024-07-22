package dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencySelectorSearchDialog(
    onDismissDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissDialog,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = CurrencyConverterTheme.colors.primaryBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Test dialog")
        }
    }
}