package com.stats.mania.adapter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.stats.mania.model.Topic
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.stats.mania.R
import com.stats.mania.model.Indicator

@Composable
fun IndicatorAdapter(
    indicator: Indicator,
    onClick : (Indicator) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onClick(indicator)
            }
            ,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
        shape = RoundedCornerShape(12.dp)
        ,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = indicator.name,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = indicator.sourceNote,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = Color.Blue,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )


        }
    }
}
