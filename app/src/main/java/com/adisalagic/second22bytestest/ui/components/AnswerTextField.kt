package com.adisalagic.second22bytestest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adisalagic.second22bytestest.ui.theme.RobotoFont
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun AnswerTextField(onValueChanged: (String) -> Unit) {
    var value by remember {
        mutableStateOf("")
    }
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontFamily = RobotoFont,
            fontSize = 18.ssp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White)
                .padding(7.sdp),
            contentAlignment = Alignment.Center
        ) {
            it()
            if (value.isBlank()) {
                Text(
                    text = "УКАЖИ ОТВЕТ",
                    fontFamily = RobotoFont,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.ssp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0x80000000)
                )
            }
        }
    }
}