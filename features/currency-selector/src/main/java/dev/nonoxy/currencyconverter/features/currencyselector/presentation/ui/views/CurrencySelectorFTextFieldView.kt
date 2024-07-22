package dev.nonoxy.currencyconverter.features.currencyselector.presentation.ui.views

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nonoxy.currencyconverter.features.currencyselector.R
import dev.nonoxy.currencyconverter.features.theme.ui.theme.CurrencyConverterTheme

@Composable
internal fun CurrencySelectorFTextFieldView(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable() (() -> Unit)? = null,
    prefix: @Composable() (() -> Unit)? = null,
    textStyle: TextStyle = CurrencyConverterTheme.typography.bodyLarge,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = CurrencyConverterTheme.colors.secondaryText,
        unfocusedTextColor = CurrencyConverterTheme.colors.secondaryText,
        focusedContainerColor = CurrencyConverterTheme.colors.primaryContainer,
        unfocusedContainerColor = CurrencyConverterTheme.colors.primaryContainer,
        unfocusedIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        focusedPlaceholderColor = CurrencyConverterTheme.colors.secondaryText,
        unfocusedPlaceholderColor = CurrencyConverterTheme.colors.secondaryText,
        focusedPrefixColor = CurrencyConverterTheme.colors.secondaryText,
        unfocusedPrefixColor = CurrencyConverterTheme.colors.secondaryText,
        focusedLabelColor = CurrencyConverterTheme.colors.secondaryText,
        unfocusedLabelColor = CurrencyConverterTheme.colors.secondaryText,
        disabledContainerColor = CurrencyConverterTheme.colors.primaryContainer,
        disabledPlaceholderColor = CurrencyConverterTheme.colors.primaryText,
        disabledIndicatorColor = Color.Transparent
    )
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = label,
        prefix = prefix,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        shape = shape,
        colors = colors
    )
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
private fun CurrencySelectorFTextFieldView_Preview() {
    CurrencyConverterTheme {
        CurrencySelectorFTextFieldView(
            modifier = Modifier.padding(32.dp),
            value = "",
            onValueChange = {},
            label = { Text(text = "From") },
            placeholder = { Text(text = "Select Base Currency") },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp),
                    tint = CurrencyConverterTheme.colors.secondaryText
                )
            }
        )
    }
}