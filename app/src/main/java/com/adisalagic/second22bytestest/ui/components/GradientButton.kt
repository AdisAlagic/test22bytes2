package com.adisalagic.second22bytestest.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    brush: Brush,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick,
        modifier,
        enabled,
        shape,
        ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        elevation,
        border,
        PaddingValues(0.dp),
        interactionSource
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush, shape)
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            with(this@Button) {
                content()
            }
        }
    }
}

@Composable
fun DefaultGradientButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
    text: String
) {
    //#FF551F, #FB1FFF
    GradientButton(
        enabled = enabled,
        onClick = onClick,
        brush = Brush.horizontalGradient(listOf(Color(0xFFFF551F), Color(0xFFFB1FFF)))
    ) {
        Text(text = text, modifier = Modifier.fillMaxWidth(), fontSize = 20.ssp, color = Color.White, textAlign = TextAlign.Center)
    }
}