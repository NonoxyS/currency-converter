package dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.onInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyselector.R
import dev.nonoxy.currencyconverter.features.currencyselector.domain.models.CurrencyInfoUI
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorEvent
import dev.nonoxy.currencyconverter.features.currencyselector.presentation.models.CurrencySelectorViewState
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun CurrencySelectorModalBottomSheet(
    viewState: CurrencySelectorViewState.Display,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    eventHandler: (CurrencySelectorEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        containerColor = CurrencyConverterTheme.colors.primaryBackground,
        windowInsets = WindowInsets(0.dp),
        modifier = Modifier.fillMaxHeight(.9f)
    ) {
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
        ) {
            var hasFocus by remember { mutableStateOf(false) }
            CurrencySelectorFTextFieldView(
                value = viewState.currencySearchTextValue,
                onValueChange = { eventHandler(CurrencySelectorEvent.OnChangeCurrencySearchValue(it)) },
                placeholder = { Text(text = stringResource(R.string.search_currency)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = CurrencyConverterTheme.colors.secondaryText,
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    if (hasFocus) {
                        IconButton(
                            onClick = {
                                if (viewState.currencySearchTextValue.isNotBlank()) {
                                    eventHandler(
                                        CurrencySelectorEvent.OnChangeCurrencySearchValue("")
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
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .onFocusChanged { hasFocus = it.isFocused }
                    .onInterceptKeyBeforeSoftKeyboard {
                        if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_BACK) {
                            focusManager.clearFocus()
                        }
                        false
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(
                    items = viewState.filteredCurrencies,
                    key = { it.currencyCode }) { currencyInfo ->
                    CurrencyItem(
                        currencyInfo = currencyInfo,
                        onItemClick = {
                            onDismiss()
                            eventHandler(CurrencySelectorEvent.OnSelectCurrency(currencyInfo))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CurrencyItem(
    currencyInfo: CurrencyInfoUI,
    onItemClick: (CurrencyInfoUI) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CurrencyConverterTheme.colors.primaryContainer)
                .clickable(
                    onClick = { onItemClick(currencyInfo) },
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = currencyInfo.currencyCode,
                style = CurrencyConverterTheme.typography.titleMedium,
                color = CurrencyConverterTheme.colors.primaryText
            )
            Text(
                text = currencyInfo.currencyName,
                style = CurrencyConverterTheme.typography.bodySmall,
                color = CurrencyConverterTheme.colors.secondaryText
            )
        }
    }
}