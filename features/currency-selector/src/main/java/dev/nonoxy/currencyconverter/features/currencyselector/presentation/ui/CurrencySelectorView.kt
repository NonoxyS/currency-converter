package dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui

import android.view.KeyEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.onInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyselector.R
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyFieldType
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorEvent
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorViewState
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views.CurrencySelectorCurrencyView
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views.CurrencySelectorFTextFieldView
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views.CurrencySelectorTopBarView
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun CurrencySelectorView(
    viewState: CurrencySelectorViewState.Display,
    eventHandler: (CurrencySelectorEvent) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
    ) {
        CurrencySelectorTopBarView()

        Text(
            text = stringResource(R.string.amount),
            style = CurrencyConverterTheme.typography.titleMedium,
            color = CurrencyConverterTheme.colors.primaryText,
            modifier = Modifier.padding(start = 16.dp)
        )

        var hasFocus by remember { mutableStateOf(false) }
        CurrencySelectorFTextFieldView(
            value = viewState.currencyAmount,
            onValueChange = { eventHandler(CurrencySelectorEvent.OnChangeCurrencyAmountValue(it)) },
            prefix = { Text(text = "${viewState.fromCurrency.currencySymbol} ") },
            trailingIcon = {
                if (hasFocus) {
                    IconButton(
                        onClick = {
                            if (viewState.currencyAmount.isNotBlank()) {
                                eventHandler(
                                    CurrencySelectorEvent.OnChangeCurrencyAmountValue("")
                                )
                            } else focusManager.clearFocus()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                            contentDescription = stringResource(R.string.clear_input),
                            tint = CurrencyConverterTheme.colors.secondaryText,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calculate),
                        contentDescription = null,
                        tint = CurrencyConverterTheme.colors.secondaryText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .onFocusChanged { hasFocus = it.isFocused }
                .onInterceptKeyBeforeSoftKeyboard {
                    if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_BACK) {
                        focusManager.clearFocus()
                    }
                    false
                }
        )

        Text(
            text = stringResource(R.string.from),
            style = CurrencyConverterTheme.typography.titleMedium,
            color = CurrencyConverterTheme.colors.primaryText,
            modifier = Modifier.padding(start = 16.dp)
        )
        CurrencySelectorCurrencyView(
            selectedCurrency = viewState.fromCurrency,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        eventHandler(
                            CurrencySelectorEvent.OnChangeCurrencyFieldClick(
                                currencyFieldType = CurrencyFieldType.CurrencyBase
                            )
                        )
                    }
                )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextButton(
                onClick = { eventHandler(CurrencySelectorEvent.SwapCurrency) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CurrencyConverterTheme.colors.primaryButton,
                    contentColor = CurrencyConverterTheme.colors.onPrimaryButton
                ),
                contentPadding = ButtonDefaults.TextButtonWithIconContentPadding
            ) {
                Text(
                    text = stringResource(R.string.swap),
                    style = CurrencyConverterTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_exchange),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    eventHandler(CurrencySelectorEvent.OnConvertCurrencyButtonClick)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CurrencyConverterTheme.colors.primaryButton,
                    contentColor = CurrencyConverterTheme.colors.onPrimaryButton
                ),
                contentPadding = ButtonDefaults.TextButtonWithIconContentPadding
            ) {
                Text(
                    text = stringResource(R.string.convert),
                    style = CurrencyConverterTheme.typography.titleSmall,
                    modifier = Modifier.padding(8.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_currency_convert),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = stringResource(R.string.to),
            style = CurrencyConverterTheme.typography.titleMedium,
            color = CurrencyConverterTheme.colors.primaryText,
            modifier = Modifier.padding(start = 16.dp)
        )
        CurrencySelectorCurrencyView(
            selectedCurrency = viewState.targetCurrency,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    onClick = {
                        eventHandler(
                            CurrencySelectorEvent.OnChangeCurrencyFieldClick(
                                currencyFieldType = CurrencyFieldType.CurrencyTarget
                            )
                        )
                    }
                )
        )
    }
}